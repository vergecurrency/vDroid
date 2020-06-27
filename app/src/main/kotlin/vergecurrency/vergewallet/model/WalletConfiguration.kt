package vergecurrency.vergewallet.model

import android.content.Context
import androidx.lifecycle.ViewModel
import vergecurrency.vergewallet.helpers.utils.WalletDataIdentifierUtils
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
    private lateinit var pin: ByteArray
    private lateinit var walletName: ByteArray
    private lateinit var gcmSpec: GCMParameterSpec
    private lateinit var id: ByteArray

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
        val hashSpec = SecretKeySpec(UUID.randomUUID().toString().toByteArray(), "HmacSHA256")
        shaHMAC.init(hashSpec)
        secSpec = SecretKeySpec(shaHMAC.doFinal(pin), "AES")
        gcmSpec = GCMParameterSpec(128, secSpec.encoded)
        this.pin = encrypt(pin)
    }

    fun setPassphrase(passphrase: ByteArray) {
        this.passphrase = encrypt(passphrase);
    }

    fun getPassphrase(): ByteArray {
        return this.passphrase;
    }

    fun getWalletName(): ByteArray {
        return this.walletName
    }

    fun getWalletId(): ByteArray {
        return this.id
    }

    fun setWalletName(name: ByteArray) {
        this.walletName = encrypt(name)
    }

    fun generateWalletId(context: Context) {
        this.id = encrypt(WalletDataIdentifierUtils.getUnusedUUID(context).toString().toByteArray())
    }

    fun isSamePin(pin: ByteArray): Boolean {
        return encrypt(pin).contentEquals(this.pin)
    }

    fun getSeed(): Array<ByteArray> {
        return seed
    }

    fun getPin(): ByteArray {
        return this.pin
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