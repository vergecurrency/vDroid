package vergecurrency.vergewallet.wallet

import android.content.Context
import com.google.crypto.tink.subtle.Hex
import io.horizontalsystems.bitcoinkit.BitcoinKit.NetworkType
import io.horizontalsystems.bitcoinkit.crypto.Base58
import io.horizontalsystems.bitcoinkit.models.Address
import io.horizontalsystems.bitcoinkit.utils.HashUtils
import io.horizontalsystems.hdwalletkit.HDKey
import org.json.JSONObject
import vergecurrency.vergewallet.Constants
import vergecurrency.vergewallet.helpers.SJCL
import vergecurrency.vergewallet.helpers.utils.ValidationUtils
import vergecurrency.vergewallet.service.model.PreferencesManager
import vergecurrency.vergewallet.service.model.wallet.AddressInfo
import vergecurrency.vergewallet.service.model.wallet.CreateAddressErrorResponse
import vergecurrency.vergewallet.service.model.wallet.WalletId
import vergecurrency.vergewallet.service.model.wallet.WalletOptions
import java.net.URI
import java.net.URISyntaxException
import java.security.KeyFactory
import java.security.Signature
import java.security.spec.PKCS8EncodedKeySpec


typealias URLCompletion = (data: String?, response: Void?, error: Exception?) -> Unit

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

    fun createWallet(walletName: String,
                     copayerName: String,
                     m: Int,
                     n: Int,
                     options: WalletOptions?,
                     completion: (error: Exception?, secret: String?) -> Void) {

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

    fun joinWallet(
            walletIdentifier: String,
            completion: (exception: Exception?) -> Void
    ) {
        val xPubKey = credentials.publicKey.publicKey.contentToString()
        val requestPubKey = credentials.requestPrivateKey.pubKey

        val encCopayerName = encryptMessage("android-copayer", credentials.sharedEncryptingKey!!)
        val copayerSignatureHash = sequenceOf(encCopayerName, xPubKey, requestPubKey).joinToString(separator = "|")
        val customData = "{\"walletPrivKey\": \"${credentials.walletPrivateKey.privKeyBytes}\"}"

        var args: JSONObject = JSONObject()

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

                if(jsonResponse.getString("code") == "COPAYER_REGISTERED"){
                    openWallet{ error ->
                        completion(error!!)
                    }
                }


            } catch (e: Exception) {
                print (error!!)
            }


        }


    }

    fun openWallet(completion: (exception : Exception?) -> Void ) {
        getRequest("/v2/wallets/?includeExtendedInfo=1"){data, _, error ->
            if(data == null){
                print(error!!)
            }
            try {
                val jsonResponse = JSONObject(data)
                completion(error!!)
            } catch (e: Exception) {
                print(error!!)
            }

        }
    }

    fun scanAddresses(completion : (error : Exception) -> Void) {
        postRequest("/v1/addresses/scan", null) {_, _, error ->
            completion(error!!)
        }
    }

    fun createAddresses(completion : (error: Exception?, address : AddressInfo?, createAddressErrorResponse : CreateAddressErrorResponse?)-> Void) {
        postRequest("/v4/addresses", null) {data, _, error ->
            if(data == null) {
                completion(error,null,null)
            }

            val addressInfo = try {AddressInfo.decode(data!!)} catch (e : Exception) {null}
            val errorResponse = try {CreateAddressErrorResponse.decode(data!!)} catch (e : Exception) {null}

            val addressByPath = try{
                credentials.priva
            } catch (e: Exception){null}
        }
    }


    fun getBalance() {
        getRequest("/v1/balance",  null)
    }

    fun getMainAddresses() {
        getRequest("/v1/addresses/",  null)
    }

    fun getTxHistory() {
        getRequest("/v1/txhistory/?includeExtendedInfo=1",  null)
    }

    fun getUnspentOutputs() {
        getRequest("/v1/utxos/",  null)
    }

    fun getSendMaxInfo() {
        getRequest("/v1/sendmaxinfo",  null)
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

    private fun getUnsignedTx(something: String): String {
        //LegacyAddress add = new LegacyAddress()


        return ""
    }

    private fun encryptMessage(plaintext: String, encryptingKey: String): String {
        val key = sjcl.base64ToBits(encryptingKey)
        return sjcl.encrypt(key, plaintext, intArrayOf(128, 1))
    }

    fun decryptMessage(cyphertext: String, encryptingKey: String): String {
        val key = sjcl.base64ToBits(encryptingKey)



        return sjcl.decrypt(key, cyphertext)

    }

    //------------------------------------------------
    // HTTP Request helpers
    //------------------------------------------------


    private fun getRequest(url: String,  completion: URLCompletion) {
        try {
            val uri = URI(String.format("%s%s", Constants.VWS_ENDPOINT, url))
            //...

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


    // Thanks to the mad verbosity of Java I can not have an enum of exceptions so
    //INNER CLASSES BABY. HUNG ME SOMEWHERE.

    internal inner class AddressToScriptException(address: Address) : Exception(address.string)

    internal inner class InvalidDeriverException(value: String) : Exception(value)

    internal inner class InvalidMessageDataException(message: String) : Exception(message)

    internal inner class InvalidWidHexException(id: String) : Exception(id)

    internal inner class InvalidAddressReceivedException(addressInfo: AddressInfo) : Exception(addressInfo.address)
}
