package vergecurrency.vergewallet.model

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import vergecurrency.vergewallet.service.model.PreferencesManager
import java.util.*
import javax.crypto.Cipher
import javax.crypto.Mac
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

class WalletConfiguration() : ViewModel() {
    private lateinit var seed: Array<ByteArray>
    private lateinit var secSpec: SecretKeySpec
    private lateinit var passphrase: ByteArray
    private lateinit var gcmSpec: GCMParameterSpec
    private val uuid: ByteArray = UUID.randomUUID().toString().toByteArray()

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
    }

    fun setPin(pin: ByteArray) {
        val shaHMAC: Mac = Mac.getInstance("HmacSHA256")
        val hashSpec = SecretKeySpec(uuid, "HmacSHA256")
        shaHMAC.init(hashSpec)
        secSpec = SecretKeySpec(shaHMAC.doFinal(pin), "AES")
        gcmSpec = GCMParameterSpec(128, secSpec.encoded)
    }

    fun setPassphrase(passphrase: ByteArray) {
        this.passphrase = encrypt(passphrase);
    }

    fun getPassphrase() : ByteArray {
        return this.passphrase;
    }

    fun getPin(): ByteArray {
        return secSpec.encoded
    }

    fun isSamePin(pin: ByteArray): Boolean {
        val shaHMAC: Mac = Mac.getInstance("HmacSHA256")
        val hashSpec = SecretKeySpec(uuid, "HmacSHA256")
        shaHMAC.init(hashSpec)
        return secSpec.encoded.contentEquals(SecretKeySpec(shaHMAC.doFinal(pin), "AES").encoded)
    }

    fun getSeed(): Array<ByteArray> {
        return seed
    }

    @Throws(Exception::class)
    fun encrypt(clear: ByteArray): ByteArray {
        val cipher: Cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, secSpec, gcmSpec)
        return cipher.doFinal(clear)
    }

    @Throws(Exception::class)
    fun decrypt(encrypted: ByteArray): ByteArray {
        val cipher: Cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, secSpec, gcmSpec)
        return cipher.doFinal(encrypted)
    }
}