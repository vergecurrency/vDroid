package vergecurrency.vergewallet.wallet


import java.security.MessageDigest
import java.util.Arrays
import java.util.Base64

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

import io.horizontalsystems.hdwalletkit.HDKey
import io.horizontalsystems.hdwalletkit.HDKeychain
import io.horizontalsystems.hdwalletkit.HDPublicKey
import vergecurrency.vergewallet.service.model.MnemonicManager
import vergecurrency.vergewallet.service.model.PreferencesManager

class Credentials {

    private val hdKeychain: HDKeychain

    //hardened at 0
    val walletPrivateKey: HDKey
        get() = hdKeychain.getKeyByPath("/0'")

    val requestWalletPrivateKey: HDKey
        get() = hdKeychain.getKeyByPath("/1'/0")


    val bip44PrivateKey: HDKey
        get() = hdKeychain.getKeyByPath("m/44'/0'/0'")

    val hdPublicKey: HDPublicKey
        get() = HDPublicKey(0, false, bip44PrivateKey)

    val personalEncryptingKey: String?
        get() {
            try {
                val data = MessageDigest.getInstance("SHA-256").digest(requestWalletPrivateKey.privKeyBytes)
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
}
