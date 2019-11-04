package vergecurrency.vergewallet.wallet

import android.content.Context

import com.google.crypto.tink.subtle.Hex

import java.net.URI
import java.net.URISyntaxException
import java.security.KeyFactory
import java.security.Signature
import java.security.spec.PKCS8EncodedKeySpec

import io.horizontalsystems.bitcoinkit.crypto.Base58
import io.horizontalsystems.bitcoinkit.models.Address
import io.horizontalsystems.bitcoinkit.utils.HashUtils
import io.horizontalsystems.hdwalletkit.HDKey
import vergecurrency.vergewallet.Constants
import vergecurrency.vergewallet.helpers.SJCL
import vergecurrency.vergewallet.helpers.utils.ValidationUtils
import vergecurrency.vergewallet.service.model.wallet.AddressInfo

class WalletClient(c: Context) {

    private val sjcl: SJCL

    private val baseUrl: String


    //------------------------------------------------
    // SJCL and various Helper methods
    //------------------------------------------------

    private val signature: String
        get() = ""

    init {
        sjcl = SJCL(c)
        baseUrl = ""

    }

    fun createWallet(walletName: String, copayerName: String, m: Int, n: Int, options: String): String {
        postRequest("/v2/wallets", null, null)

        return ""
    }

    fun scanAddresses() {
        postRequest("/v1/addresses/scan", null, null)
    }

    /**
     *
     */
    fun createAddresses() {
        postRequest("/v4/addresses", null, null)
    }


    fun getBalance() {
        getRequest("/v1/balance", null, null)
    }

    fun getMainAddresses() {
        getRequest("/v1/addresses/", null, null)
    }

    fun getTxHistory() {
        getRequest("/v1/txhistory/?includeExtendedInfo=1", null, null)
    }

    fun getUnspentOutputs() {
        getRequest("/v1/utxos/", null, null)
    }

    fun getSendMaxInfo() {
        getRequest("/v1/sendmaxinfo", null, null)
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


    fun postRequest(url: String, jsonArgs: String?, escape: String?) {
        try {
            val uri = URI(String.format("%s%s", Constants.VWS_ENDPOINT, url))
            //...

        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }

    }

    fun getRequest(url: String, jsonArgs: String?, escape: String?) {
        try {
            val uri = URI(String.format("%s%s", Constants.VWS_ENDPOINT, url))
            //...

        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }

    }
    // Thanks to the mad verbosity of Java I can not have an enum of exceptions so
    //INNER CLASSES BABY. HUNG ME SOMEWHERE.

    internal inner class AddressToScriptException(address: Address) : Exception(address.string)

    internal inner class InvalidDeriverException(value: String) : Exception(value)

    internal inner class InvalidMessageDataException(message: String) : Exception(message)

    internal inner class InvalidWidHexException(id: String) : Exception(id)

    internal inner class InvalidAddressReceivedException(addressInfo: AddressInfo) : Exception(addressInfo.address)
}
