package vergecurrency.vergewallet.wallet


import io.horizontalsystems.bitcoinkit.BitcoinKit
import io.horizontalsystems.hdwalletkit.*
import vergecurrency.vergewallet.service.model.MnemonicManager
import vergecurrency.vergewallet.service.model.PreferencesManager
import java.security.MessageDigest
import java.security.PrivateKey
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class Credentials(mnemonic: Array<String>, passphrase: String, networkType: BitcoinKit.NetworkType = BitcoinKit.NetworkType.MainNet) {

    var seed: ByteArray = Mnemonic().toSeed(mnemonic.toList(), passphrase)
    var network: BitcoinKit.NetworkType = networkType


    private val privateKey: HDKey
    get() = HDKeyDerivation.createRootKey(seed)

    //hardened at 0
    val walletPrivateKey: HDKey
        get() = HDKeyDerivation.deriveChildKey(privateKey,0,true)

    val requestPrivateKey: HDKey
        get() = HDKeyDerivation.deriveChildKey(HDKeyDerivation.deriveChildKey(privateKey,1,true),0,false)


    val bip44PrivateKey: HDKey
        get() = HDKeyDerivation.deriveChildKey(HDKeyDerivation.deriveChildKey(HDKeyDerivation.deriveChildKey(privateKey,44,true),0,true),0,true)

    val publicKey: HDPublicKey
        get() = HDPublicKey(0, false, bip44PrivateKey)


    val personalEncryptingKey: String?
        get() {
            try {
                val data = MessageDigest.getInstance("SHA-256").digest(requestPrivateKey.privKeyBytes)
                val key = "personalKey".toByteArray(Charsets.UTF_8)
                val mac = Mac.getInstance("HmacSHA256")
                mac.init(SecretKeySpec(key, "HmacSHA256"))
                return Base64.getEncoder().encodeToString(Arrays.copyOf(mac.doFinal(data), 16))
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }

        }

    val sharedEncryptingKey: String?
        get() {
            try {
                val data = MessageDigest.getInstance("SHA-256").digest(walletPrivateKey.privKeyBytes)
                return Base64.getEncoder().encodeToString(Arrays.copyOf(data, 16))
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }

        }


    fun privateKeyBy(path: String, privateKey: HDKey) :ByteArray {
        var key = privateKey

        for (deriver in path.replace("m/", "").split("/")) {
            try {
                var deriverInt = Integer.parseInt(deriver)
                key = HDKeyDerivation.deriveChildKey(key,deriverInt, false)

            } catch (e: NumberFormatException) {
                throw Exception("Invalid deriver exception")
            }
        }
        return key.privKeyBytes
    }

}
