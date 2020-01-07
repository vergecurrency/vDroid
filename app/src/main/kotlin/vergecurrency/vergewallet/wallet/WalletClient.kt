package vergecurrency.vergewallet.wallet

import android.content.Context
import com.google.crypto.tink.subtle.Hex
import io.horizontalsystems.bitcoinkit.BitcoinKit.NetworkType
import io.horizontalsystems.bitcoinkit.crypto.Base58
import io.horizontalsystems.bitcoinkit.models.*
import io.horizontalsystems.bitcoinkit.network.MainNet
import io.horizontalsystems.bitcoinkit.transactions.scripts.Script
import io.horizontalsystems.bitcoinkit.utils.AddressConverter
import io.horizontalsystems.bitcoinkit.utils.HashUtils
import io.horizontalsystems.hdwalletkit.HDKey
import io.horizontalsystems.hdwalletkit.HDKeyDerivation
import io.horizontalsystems.hdwalletkit.HDPublicKey
import io.realm.Realm
import io.realm.RealmList
import org.json.JSONObject
import vergecurrency.vergewallet.Constants
import vergecurrency.vergewallet.helpers.SJCL
import vergecurrency.vergewallet.helpers.utils.ArrayUtils
import vergecurrency.vergewallet.helpers.utils.ValidationUtils
import vergecurrency.vergewallet.service.model.PreferencesManager
import vergecurrency.vergewallet.service.model.WatchRequestCredentials
import vergecurrency.vergewallet.service.model.wallet.*
import java.net.URI
import java.net.URISyntaxException
import java.nio.charset.Charset
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.Signature
import java.security.spec.PKCS8EncodedKeySpec


typealias URLCompletion = (data: String?, response: Void?, error: Exception?) -> Unit
typealias TxProposalCompletion = (txp: TxProposalResponse, errorResponse: TxProposalErrorResponse, error: Exception) -> Unit

class WalletClient {


    var sjcl: SJCL
    val baseUrl = ""
    var network: NetworkType = NetworkType.MainNet
    var credentials: Credentials

    constructor(c: Context, creds: Credentials) {
        credentials = creds
        sjcl = SJCL(c)

    }

    private val signature: String
        get() = ""


    fun resetServiceUrl(baseUrl: String) {

    }

    //Private Methods

    private fun getRequest(url: String, completion: URLCompletion) {
        try {
            val uri = URI(String.format("%s%s", Constants.VWS_ENDPOINT, url))

            val copayerId = getCoPayerId()
            var signature = try {  getSignature(url, "get")          } catch(e: Exception) { return completion(null, null, null) }




        } catch (e: URISyntaxException) {
            return completion(null, null, null)
        }
        return completion(null, null, null)
    }

    private fun postRequest(url: String, jsonArgs: JSONObject?, completion: URLCompletion) {
        try {
            val uri = URI(String.format("\\%s\\%s", Constants.VWS_ENDPOINT, url))

            try {

            } catch (e: Exception) {

            }

            //...

        } catch (e: URISyntaxException) {

            return completion(null, null, null)
        }
        return completion(null, null, null)
    }


    fun deleteRequest() {

    }

    fun getCoPayerId(): String {
        return ""
    }

    //Interact with wallet

    fun createWallet(walletName: String, copayerName: String, m: Int, n: Int, options: WalletOptions?, completion: (error: Exception?, secret: String?) -> Void) {

        val encWalletName = encryptMessage(walletName, credentials.sharedEncryptingKey!!)

        var args: JSONObject = JSONObject()
        args.put("name", encWalletName)
        //TODO : compare with Swen's lib
        args.put("pubKey", credentials.walletPrivateKey.pubKeyHash.toString())
        args.put("m", m)
        args.put("n", n)
        args.put("coin", "xvg")
        args.put("network", "livenet")

        postRequest("/v2/wallets", args) { data, _, exception ->
            if (data != null) {
                try {
                    val walletId = WalletId.decode(data)

                    PreferencesManager.walletId = walletId.identifier
                    PreferencesManager.walletName = walletName
                    PreferencesManager.walletSecret = buildSecret(walletId.identifier!!)

                    completion(null, walletId.identifier)
                } catch (e: Exception) {
                    completion(e, null)
                }
            } else {
                completion(exception, null)
            }

        }

    }

    fun joinWallet(walletIdentifier: String, completion: (exception: Exception?) -> Void) {
        val xPubKey = credentials.publicKey.publicKey.contentToString()
        val requestPubKey = credentials.requestPrivateKey.pubKey

        val encCopayerName = encryptMessage("android-copayer", credentials.sharedEncryptingKey!!)
        val copayerSignatureHash = sequenceOf(encCopayerName, xPubKey, requestPubKey).joinToString(separator = "|")
        val customData = "{\"walletPrivKey\": \"${credentials.walletPrivateKey.privKeyBytes}\"}"

        var args = JSONObject()

        args.put("walletId", walletIdentifier)
        args.put("coin", "xvg")
        args.put("name", encCopayerName)
        args.put("xPubKey", xPubKey)
        args.put("requestPubKey", requestPubKey)
        args.put("customData", encryptMessage(customData, credentials.personalEncryptingKey!!))
        args.put("copayerSignature", signMessage(copayerSignatureHash, credentials.walletPrivateKey))

        postRequest("/v2/wallets/$walletIdentifier/copayers/", args) { data, _, error ->
            if (data == null) {
                print(error!!)
            }
            try {
                val jsonResponse = JSONObject(data)

                if (jsonResponse.getString("code") == "WALLET_NOT_FOUND") {
                    completion(Exception("Wallet not found - 404"))
                }

                if (jsonResponse.getString("code") == "COPAYER_REGISTERED") {
                    openWallet { error ->
                        completion(error!!)
                    }
                }
            } catch (e: Exception) {
                print(error!!)
            }
        }
    }

    fun openWallet(completion: (exception: Exception?) -> Void) {
        getRequest("/v2/wallets/?includeExtendedInfo=1") { data, _, error ->
            if (data == null) {
                print(error!!)
            }
            try {
                val jsonResponse = JSONObject(data!!)
                completion(error!!)
            } catch (e: Exception) {
                print(error!!)
            }
        }
    }

    //Interact with wallet addresses

    fun scanAddresses(completion: (error: Exception) -> Void) {
        postRequest("/v1/addresses/scan", null) { _, _, error ->
            completion(error!!)
        }
    }

    fun createAddresses(completion: (error: Exception?, address: AddressInfo?, createAddressErrorResponse: CreateAddressErrorResponse?) -> Void) {
        postRequest("/v4/addresses", null) { data, _, error ->
            if (data == null) {
                completion(error, null, null)
            }

            val addressInfo = try {
                AddressInfo.decode(data!!)
            } catch (e: Exception) {
                null
            }
            val errorResponse = try {
                CreateAddressErrorResponse.decode(data!!)
            } catch (e: Exception) {
                null
            }

            val addressByPath = try {
                getLegacyAddr(HDPublicKey(0, false, credentials.privateKeyBy(addressInfo!!.path
                        ?: "", credentials.bip44PrivateKey)).publicKey)
            } catch (e: Exception) {
                null
            }

            //verify that one pal
            if (addressInfo!!.address != addressByPath!!.string) {
                completion(InvalidAddressReceivedException(addressInfo), null, null)
            }

            completion(null, addressInfo, errorResponse)
        }
    }

    fun getMainAddresses(options: WalletAddressesOptions? = null, completion: (addresses: Array<AddressInfo>) -> Void) {
        var args: ArrayList<String> = ArrayList()
        var qs = ""

        if (options!!.limit != null) {
            args.add("limit=${options.limit}")
        }

        if (!options.isReverse) {
            args.add("reverse=1")
        }

        if (args.size > 0) {
            qs = "?${args.joinToString { "&" }}"
        }

        getRequest("v1/addresses/$qs") { data, _, error ->
            if (data != null) {
                try {
                    completion(AddressInfo.decodeArray(data))
                } catch (e: Exception) {
                    completion(emptyArray())
                }
            }
            completion(emptyArray())
        }
    }

    fun getLegacyAddr(address: ByteArray): LegacyAddress {
        return AddressConverter(MainNet()).convert(address) as LegacyAddress
    }

    //Wallet info methods

    fun getBalance(completion: (error: Exception?, balanceInfo: WalletBalanceInfo?) -> Void) {
        getRequest("/v1/balance") { data, _, error ->
            if (data == null) {
                completion(error!!, null)
            }
            try {
                completion(error!!, WalletBalanceInfo.decode(data!!))
            } catch (e: Exception) {
                completion(error!!, null)
            }


        }
    }


    fun getTxHistory(skip: Int? = null, limit: Int? = null, completion: (transactions: Array<TxHistory>) -> Void) {

        var url = "/v1/txhistory/?includeExtendedInfo=1"
        if (skip != null && limit != null) {
            url = "$url&skip=$skip&limit=$limit"
        }

        getRequest(url) { data, _, error ->
            if (data == null) {
                completion(emptyArray())
            }

            try {
                val transactions = TxHistory.decodeArray(data!!)
                val transformedTransactions = Array(transactions.size) { TxHistory() }

                for ((i, transaction) in transactions.withIndex()) {

                    if (transaction.message != null) {
                        transaction.message = decryptMessage(transaction.message!!, credentials.sharedEncryptingKey!!)
                    }
                    transformedTransactions[i] = transaction
                }
                completion(transformedTransactions)

            } catch (e: Exception) {
                completion(emptyArray())

            }
        }
    }

    //TODO : But Not Today
    fun getUnspentOutputs(address: String? = null, completion: (unspentOutputs: Array<UnspentOutput>) -> Void) {
        //  getRequest("/v1/utxos/",  null)
    }

    fun getSendMaxInfo(completion: (sendMaxInfo: SendMaxInfo?) -> Void) {
        getRequest("/v1/sendmaxinfo") { data, _, _ ->
            if (data != null) {
                completion(try {
                    SendMaxInfo.decode(data)
                } catch (e: Exception) {
                    null
                })
            }
            completion(null)
        }
    }

    fun watchRequestCredentialsForMethodPath(path: String): WatchRequestCredentials {
        var result = WatchRequestCredentials(null, null, null)
        //ToDo : add addUrlReference extension
        val referencedUrl = path

        val url = "$baseUrl$referencedUrl"
        val copayerId = getCoPayerId()

        if (referencedUrl.contains("/v1/balance")) {
            var signature = try {
                getSignature(referencedUrl, method = "get")
            } catch (e: Exception) {
                null
            }

            result.url = url
            result.copayerId = copayerId
            result.signature = signature

        }
        return result
    }

    // Tx proposal methods

    fun getSignature(url: String, method: String, arguments: String = "{}"): String {
        return ""
    }


    @Throws(InvalidMessageDataException::class)
    private fun signMessage(message: String, privKey: HDKey): String {

        var result: String

        if (ValidationUtils.isValidUTF8(message)) {

            try {
                val sig = Signature.getInstance("SHA256withECDSA")
                sig.initSign(KeyFactory.getInstance("EC").generatePrivate(PKCS8EncodedKeySpec(privKey.privKeyBytes)))
                sig.update(HashUtils.doubleSha256(message.toByteArray()))
                result = String(sig.sign())

            } catch (e: Exception) {
                e.printStackTrace()
                result = "SignatureException"
            }

        } else {
            throw InvalidMessageDataException(message)
        }

        return result
    }

    private fun encryptMessage(plaintext: String, encryptingKey: String): String {
        val key = sjcl.base64ToBits(encryptingKey)
        return sjcl.encrypt(key, plaintext, intArrayOf(128, 1))
    }

    fun decryptMessage(cyphertext: String, encryptingKey: String): String {
        val key = sjcl.base64ToBits(encryptingKey)



        return sjcl.decrypt(key, cyphertext)

    }

    @Throws(InvalidWidHexException::class)
    private fun buildSecret(walletId: String): String {
        try {
            val widHex = Hex.decode(walletId.replace("-", ""))
            val widBase58 = String.format("%1$-" + Base58.encode(widHex).length + "s", Base58.encode(widHex)).replace(" ", "0")

            return "\\($widBase58)\\(credentials.privateKey.toWIF())Lxvg"

        } catch (e: Exception) {
            throw InvalidWidHexException(walletId)
        }

    }

    private fun getUnsignedTx(txp: TxProposalResponse): UnsignedTransaction {
        var output: TxOutput
        if (txp.outputs != null && !txp.outputs!!.isEmpty()) {
            throw NoOutputFoundException("")
        } else {
            output = txp.outputs!!.first()
        }

        val changeAddress = LegacyAddress(txp.changeAddress!!.address!!, byteArrayOf(1), AddressType.P2PKH)
        val toAddress = LegacyAddress(output.toAddress!!, byteArrayOf(1), AddressType.P2PKH)

        val unspentOutputs = txp.inputs
        val unspentTransactions: Array<UnspentTransaction> = unspentOutputs!!.map { output -> output.asUnspentTransaction() }.toTypedArray()

        val amount = txp.amount

        //I LITERALLY SPENT ONE DAY ON THIS. DO YOU SEE THE PROBLEM? DO YOU? I DID FUCKING NOT.
        //val totalAmount   = unspentTransactions.fold(0) {a , b -> a + b.output.value }
        //val totalAmount   = unspentTransactions.fold(0L) {a , b -> a + b.output.value }
        val totalAmount = unspentTransactions.fold(0L) { a, b -> a + b.output.value }
        val change = totalAmount - amount - txp.fee


        try {
            val lockingScriptChange = Script(changeAddress.hash)
            val lockingScriptTo = Script(toAddress.hash)

            val changeOutput = TransactionOutput().apply {
                value = change
                lockingScript = getDataFromScript(lockingScriptChange)
            }

            val toOutput = TransactionOutput().apply {
                value = amount
                lockingScript = getDataFromScript(lockingScriptTo)
            }

            val unsignedInputs = unspentOutputs.map { output -> output.asInputTransaction() }

            var tOutputs : ArrayList<TransactionOutput> = ArrayList()
            tOutputs.add(toOutput)


            //TODO : Add stealth support
            /*if( output.isStealth) {
                 val ephemeral =
             }*/

            if (change > 0) {
                tOutputs.add(changeOutput)
            }
            tOutputs = ArrayUtils.filterByIndices(txp.outputOrder!!.map{it}, tOutputs)
            val realm : Realm = Realm.getDefaultInstance()

            val tx = Transaction().apply {
                version = 1
                timestamp = txp.createdOn.toLong()
                lockTime = 0
            }

            //add realmlist object
            realm.beginTransaction()
            tx.inputs = RealmList<TransactionInput>()
            tx.inputs.addAll(unsignedInputs)
            tx.outputs = RealmList<TransactionOutput>()
            tx.outputs.addAll(tOutputs)
            realm.commitTransaction()

            return UnsignedTransaction(tx, unspentTransactions)
        } catch (e: Exception) {
            throw AddressToScriptException(changeAddress)
        }
    }

    private fun signTx(unsignedTx: UnsignedTransaction, keys: Array<HDKey>): Array<String> {
        var inputsToSign = unsignedTx.tx.inputs
        var transactionToSign = Transaction().apply {
            version = unsignedTx.tx.version
            timestamp = unsignedTx.tx.timestamp
            inputs = inputsToSign
            outputs = unsignedTx.tx.outputs
            lockTime = unsignedTx.tx.lockTime

        }


        var hexes = ArrayList<String>()

        for ((i, utxo) in unsignedTx.utxos.withIndex()) {
            val pubkeyHash = utxo.output.lockingScript.slice(3..22).toByteArray()

            val keysOfUtxo = keys.filter { it.pubKeyHash!!.contentEquals(pubkeyHash) }
            if (keysOfUtxo.isEmpty()) {
                print("No keys")
                continue
            }

            val sighash = transactionToSign.toSignatureByteArray(i, false)

            var signature: String

            try {
                val sig = Signature.getInstance("SHA256withECDSA")
                sig.initSign(KeyFactory.getInstance("EC").generatePrivate(PKCS8EncodedKeySpec(keysOfUtxo.first().privKeyBytes)))
                sig.update(HashUtils.doubleSha256(sighash))
                signature = sig.sign().toString(Charset.forName("UTF-8"))

            } catch (e: Exception) {
                e.printStackTrace()
                signature = ""
            }
            hexes.add(signature)

        }

        return hexes.toList().toTypedArray()
    }


    private fun getDataFromScript(address : Script) : ByteArray {
        return address.chunks.fold(ByteArray(address.chunks.size)){a,b -> a+b.data!!}
    }

    //INNER CLASSES BABY. HUNG ME SOMEWHERE.

    internal inner class AddressToScriptException(address: Address) : Exception(address.string)

    internal inner class InvalidDeriverException(value: String) : Exception(value)

    internal inner class NoOutputFoundException(value: String) : Exception(value)

    internal inner class InvalidMessageDataException(message: String) : Exception(message)

    internal inner class InvalidWidHexException(id: String) : Exception(id)

    internal inner class InvalidAddressReceivedException(addressInfo: AddressInfo) : Exception(addressInfo.address)
}
