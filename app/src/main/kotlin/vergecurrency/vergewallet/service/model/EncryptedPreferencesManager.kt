package vergecurrency.vergewallet.service.model;

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class EncryptedPreferencesManager private constructor(context: Context) {

    init {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        encryptedPreferences = EncryptedSharedPreferences.create(
                "com.vergecurrency.vergewallet.secrets",
                masterKeyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

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


        private var encryptedPreferences: SharedPreferences? = null
        private var INSTANCE: EncryptedPreferencesManager? = null

        //--------Singleton methods
        fun init(context: Context): EncryptedPreferencesManager? {
            if (EncryptedPreferencesManager.INSTANCE != null) {
                throw AssertionError("You already initialized an object of this type")
            } else {
                EncryptedPreferencesManager.INSTANCE = EncryptedPreferencesManager(context)
                return EncryptedPreferencesManager.INSTANCE
            }
        }

        //-------Pin
        var pin: String?
            get() = encryptedPreferences!!.getString(PIN, "")
            set(pin) = encryptedPreferences!!.edit().putString(PIN, pin).apply()

        //-------Pin digits
        var pinCount: Int
            get() = encryptedPreferences!!.getInt(PIN_COUNT, 6)
            set(pinCount) = encryptedPreferences!!.edit().putInt(PIN_COUNT, pinCount).apply()

        //--------12words mnemonic
        var mnemonic: String?
            get() = encryptedPreferences!!.getString(MNEMONIC, null)
            set(mnemonic) = encryptedPreferences!!.edit().putString(MNEMONIC, mnemonic).apply()

        //---------Preferred currency
        var preferredCurrency: String?
            get() = encryptedPreferences!!.getString(PREFERRED_CURRENCY, Currency("United States Dollar", "USD").currencyAsJSON)
            set(preferredCurrency) = encryptedPreferences!!.edit().putString(PREFERRED_CURRENCY, preferredCurrency).apply()

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
        var walletSecret: String?
            get() = encryptedPreferences!!.getString(WALLET_SECRET, null)
            set(secret) {
                var wSecret = secret
                if (wSecret == null) {
                    wSecret = ""
                }
                encryptedPreferences!!.edit().putString(WALLET_SECRET, wSecret).apply()
            }

        //--------walletsecret
        var deviceToken: String?
            get() = encryptedPreferences!!.getString(DEVICE_TOKEN, "")
            set(token) = encryptedPreferences!!.edit().putString(DEVICE_TOKEN, token).apply()

        //--------auth for unlocking wallet
        var localAuthForWalletUnlock: Boolean
            get() = encryptedPreferences!!.getBoolean(AUTH_UNLOCK_WALLET, false)
            set(value) = encryptedPreferences!!.edit().putBoolean(AUTH_UNLOCK_WALLET, value).apply()

        //--------auth for sending xvg
        var localAuthForSendingXVG: Boolean
            get() = encryptedPreferences!!.getBoolean(AUTH_SEND_XVG, false)
            set(value) = encryptedPreferences!!.edit().putBoolean(AUTH_SEND_XVG, value).apply()

        //--------walletid
        var walletId: String?
            get() = encryptedPreferences!!.getString(WALLET_ID, null)
            set(id) {
                var wId = id
                if (wId == null) {
                    wId = ""
                }
                encryptedPreferences!!.edit().putString(WALLET_ID, wId).apply()
            }

        //-------User passphrase
        var passphrase: String?
            get() = encryptedPreferences!!.getString(PASSPHRASE, "mnemonic")
            set(passphrase) = encryptedPreferences!!.edit().putString(PASSPHRASE, passphrase).apply()

        //--------walletname
        var walletName: String?
            get() = encryptedPreferences!!.getString(WALLET_NAME, null)
            set(name) {
                var wName = name
                if (wName == null) {
                    wName = ""
                }
                encryptedPreferences!!.edit().putString(WALLET_NAME, wName).apply()
            }

    }

}
