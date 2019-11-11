package vergecurrency.vergewallet.wallet


import io.horizontalsystems.hdwalletkit.HDKey
import io.horizontalsystems.hdwalletkit.HDKeychain
import io.horizontalsystems.hdwalletkit.HDPublicKey
import vergecurrency.vergewallet.service.model.MnemonicManager
import vergecurrency.vergewallet.service.model.PreferencesManager
import java.security.MessageDigest
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class Credentials {

    private val hdKeychain: HDKeychain

    //TODO : Implement HDKeyDerivation


    //hardened at 0
    val walletPrivateKey: HDKey
        get() = hdKeychain.getKeyByPath("/0'")

    val requestPrivateKey: HDKey
        get() = hdKeychain.getKeyByPath("/1'/0")


    val bip44PrivateKey: HDKey
        get() = hdKeychain.getKeyByPath("m/44'/0'/0'")

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

    init {
        hdKeychain = initHDKeyChain()
    }

    fun initHDKeyChain(): HDKeychain {
        val mnemonic = MnemonicManager.getMnemonicFromJSON(PreferencesManager.mnemonic!!)!!
        val passphrase = PreferencesManager.passphrase

        val seed = io.horizontalsystems.hdwalletkit.Mnemonic().toSeed(listOf(*mnemonic), passphrase!!)
        return HDKeychain(seed, true)
    }

    fun privateKeyBy(path: String, privateKey: HDKey): HDKey {
        var key = privateKey
        var privateKey : HDKey
        for (deriver in path.replace("m/", "").split("/")) {
            try {
                var deriverInt = Integer.parseInt(deriver)
                //key = key.path.

                //todo : need a HDKeyDerivation Object
            } catch (e: NumberFormatException) {
                throw Exception("Invalid deriver exception")
            }

        }
    }
}
