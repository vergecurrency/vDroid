package vergecurrency.vergewallet.model

import android.util.Base64.encodeToString
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import vergecurrency.vergewallet.service.model.PreferencesManager
import java.util.*
import javax.crypto.Cipher
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class WalletConfiguration() : ViewModel() {
    private lateinit var seed: Array<ByteArray>
    private lateinit var secSpec: SecretKeySpec
    private val transformation = "AES"

    fun generateMnemonics() {
        //if first launch
        if (PreferencesManager.isFirstLaunch) {
            generateMnemonic()
        }
    }

    private fun generateMnemonic() {
        seed = io.horizontalsystems.hdwalletkit.Mnemonic().generate(io.horizontalsystems.hdwalletkit.Mnemonic.Strength.Default).map { word ->
            this.encrypt(word.toByteArray())
        }.toTypedArray()
        //TODO
        //EncryptedPreferencesManager.mnemonic = mnemonicAsJSON
    }

    val mnemonicAsJSON: ByteArray
        get() = Gson().toJson(seed).toByteArray()

    fun setPin(pin: ByteArray) {
        val shaHMAC: Mac = Mac.getInstance("HmacSHA256")
        val hashSpec = SecretKeySpec(UUID.randomUUID().toString().toByteArray(), "HmacSHA256")
        shaHMAC.init(hashSpec)
        secSpec = SecretKeySpec(shaHMAC.doFinal(pin), transformation)
    }

    fun getPin(): ByteArray {
        return secSpec.encoded
    }

    fun getSeed(): Array<ByteArray> {
        return seed.map { word -> decrypt(word) }.toTypedArray()
    }

    @Throws(Exception::class)
    fun encrypt(clear: ByteArray): ByteArray {
        val cipher: Cipher = Cipher.getInstance(transformation)
        cipher.init(Cipher.ENCRYPT_MODE, secSpec)
        return cipher.doFinal(clear)
    }

    @Throws(Exception::class)
    fun decrypt(encrypted: ByteArray): ByteArray {
        val cipher: Cipher = Cipher.getInstance(transformation)
        cipher.init(Cipher.DECRYPT_MODE, secSpec)
        return cipher.doFinal(encrypted)
    }
}