package vergecurrency.vergewallet.service.model;

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import vergecurrency.vergewallet.helpers.utils.WalletDataIdentifierUtils
import java.util.*
import javax.crypto.Mac
import javax.crypto.Mac.getInstance
import javax.crypto.spec.SecretKeySpec
import kotlin.collections.ArrayList

class EncryptedPreferencesManager private constructor() {

    companion object {
        private const val PIN = "pin"
        private const val PIN_COUNT = "pinCount"
        private const val PREFERRED_CURRENCY = "preferredCurrency"
        private const val USING_TOR = "usingTor"
        private const val MNEMONIC = "mnemonic"
        private const val AMOUNT = "amount"
        private const val AUTH_UNLOCK_WALLET = "authUnlockWallet"
        private const val AUTH_SEND_XVG = "authSendXVG"
        private const val WALLET_ID = "walletId"
        private const val WALLET_NAME = "walletName"
        private const val WALLET_SECRET = "walletSecret"
        private const val DEVICE_TOKEN = "deviceToken"
        private const val PASSPHRASE = "passphrase"
        private const val REALM_ENCRYPTION_KEY = "realm_encryption_key"


        private var encryptedPreferences: SharedPreferences? = null
        private var INSTANCE: EncryptedPreferencesManager? = null

        //--------Singleton methods
        fun init(): EncryptedPreferencesManager? {
            if (EncryptedPreferencesManager.INSTANCE != null) {
                throw AssertionError("You already initialized an object of this type")
            } else {
                EncryptedPreferencesManager.INSTANCE = EncryptedPreferencesManager()
                return EncryptedPreferencesManager.INSTANCE
            }
        }

        //-------Pin
        var pin: CharArray?
            get() = encryptedPreferences!!.getString(PIN, "")!!.toCharArray()
            set(pin) = encryptedPreferences!!.edit().putString(PIN, String(pin!!)).apply()

        //-------Pin digits
        var pinCount: Int
            get() = encryptedPreferences!!.getInt(PIN_COUNT, 6)
            set(pinCount) = encryptedPreferences!!.edit().putInt(PIN_COUNT, pinCount).apply()

        //--------12words mnemonic
        var mnemonic: CharArray?
            get() = encryptedPreferences!!.getString(MNEMONIC, null)!!.toCharArray()
            set(mnemonic) = encryptedPreferences!!.edit().putString(MNEMONIC, String(mnemonic!!)).apply()

        //---------Preferred currency
        var preferredCurrency: CharArray?
            get() = encryptedPreferences!!.getString(PREFERRED_CURRENCY, Currency("United States Dollar", "USD".toCharArray()).currencyAsJSON)!!.toCharArray()
            set(preferredCurrency) = encryptedPreferences!!.edit().putString(PREFERRED_CURRENCY, String(preferredCurrency!!)).apply()

        //---------Using Tor
        var usingTor: Boolean
            get() = encryptedPreferences!!.getBoolean(USING_TOR, false)
            set(isUsingTor) = encryptedPreferences!!.edit().putBoolean(USING_TOR, isUsingTor).apply()

        //---------Wallet amount

        var amount: Float
            get() = encryptedPreferences!!.getFloat(AMOUNT, 0f)
            set(amount) {
                var wAmount = amount
                if (wAmount < 0f) {
                    wAmount = 0f
                }
                encryptedPreferences!!.edit().putFloat(AMOUNT, wAmount).apply()
            }

        //--------walletsecret
        var walletSecret: CharArray?
            get() = encryptedPreferences!!.getString(WALLET_SECRET, null)!!.toCharArray()
            set(secret) {
                var wSecret = secret
                if (wSecret == null) {
                    wSecret = "".toCharArray()
                }
                encryptedPreferences!!.edit().putString(WALLET_SECRET, String(wSecret)).apply()
            }

        //--------deviceToken
        var deviceToken: CharArray?
            get() = encryptedPreferences!!.getString(DEVICE_TOKEN, "")!!.toCharArray()
            set(token) = encryptedPreferences!!.edit().putString(DEVICE_TOKEN, String(token!!)).apply()

        //--------auth for unlocking wallet
        var localAuthForWalletUnlock: Boolean
            get() = encryptedPreferences!!.getBoolean(AUTH_UNLOCK_WALLET, false)
            set(value) = encryptedPreferences!!.edit().putBoolean(AUTH_UNLOCK_WALLET, value).apply()

        //--------auth for sending xvg
        var localAuthForSendingXVG: Boolean
            get() = encryptedPreferences!!.getBoolean(AUTH_SEND_XVG, false)
            set(value) = encryptedPreferences!!.edit().putBoolean(AUTH_SEND_XVG, value).apply()

        //--------walletid
        var walletId: CharArray?
            get() = encryptedPreferences!!.getString(WALLET_ID, null)!!.toCharArray()
            set(id) {
                var wId = id
                if (wId == null) {
                    wId = "".toCharArray()
                }
                encryptedPreferences!!.edit().putString(WALLET_ID, String(wId)).apply()
            }

        //-------User passphrase
        var passphrase: CharArray?
            get() = encryptedPreferences!!.getString(PASSPHRASE, "mnemonic")!!.toCharArray()
            set(passphrase) = encryptedPreferences!!.edit().putString(PASSPHRASE, String(passphrase!!)).apply()

        //-------realm encryption key
        var realmEncryptionKey: CharArray? = null
            get() = encryptedPreferences!!.getString(REALM_ENCRYPTION_KEY, null)!!.toCharArray()

        //--------walletname
        var walletName: CharArray?
            get() = encryptedPreferences!!.getString(WALLET_NAME, null)!!.toCharArray()
            set(name) {
                var wName = name
                if (wName == null) {
                    wName = "".toCharArray()
                }
                encryptedPreferences!!.edit().putString(WALLET_NAME, String(wName)).apply()
            }

        fun getOrCreateEncryptedSharedPreferences(context: Context, walletNameExt: String) {
            // Custom Advanced Master Key
            val advancedSpec = KeyGenParameterSpec.Builder(
                    WalletDataIdentifierUtils.getMasterKeyAlias(walletNameExt),
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            ).apply {
                setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                setKeySize(256)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    setUnlockedDeviceRequired(true)
                }
            }.build()

            val masterKeyAlias = MasterKeys.getOrCreate(advancedSpec)

            //Create encrypted shared preferences
            encryptedPreferences = EncryptedSharedPreferences.create(
                    WalletDataIdentifierUtils.getEncryptedSharedPreferencesNameByUsersWalletName(walletNameExt),
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)

            if (realmEncryptionKey == null) {
                val shaHMAC: Mac = getInstance("HmacSHA512")
                val realmEncryptionKey = SecretKeySpec(UUID.randomUUID().toString().toByteArray(), "HmacSHA512")
                shaHMAC.init(realmEncryptionKey)
                encryptedPreferences!!.edit().putString(REALM_ENCRYPTION_KEY, shaHMAC.doFinal(walletName.toString().toByteArray()).toString())
            }
        }

    }

    fun getEncryptedPreferences(context: Context): ArrayList<String> {
        val existingWalletFiles: ArrayList<String> = ArrayList();
        for (file in context.dataDir.listFiles()) {
            if (file.absolutePath.endsWith("vergecurrency.vergewallet/shared_prefs")) {
                if (file.listFiles().size > 0) {
                    for (prefs in file.listFiles()) {
                        if (WalletDataIdentifierUtils.isEncryptedSharedPreferences(file.name)) {
                            existingWalletFiles.add(file.name)
                            getOrCreateEncryptedSharedPreferences(context, "")
                        }
                    }
                }
            }

        }
        return existingWalletFiles;
    }


}
