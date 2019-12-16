package vergecurrency.vergewallet.service.model

import android.content.Context
import android.content.SharedPreferences

import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

import java.io.IOException
import java.security.GeneralSecurityException

import vergecurrency.vergewallet.Constants


/**
 * Warning fella : this class is a singleton object if you are wondering why I did that shitshow ;)
 */
class PreferencesManager private constructor(context: Context) {

    init {
        preferences = context.getSharedPreferences("com.vergecurrency.vergewallet", Context.MODE_PRIVATE)
    }

    companion object {
        private val FIRST_LAUNCH = "firstlaunch"
        private val PIN = "pin"
        private val PIN_COUNT = "pinCount"
        private val PREFERRED_CURRENCY = "preferredCurrency"
        private val USING_TOR = "usingTor"
        private val LANGUAGE = "language"
        private val MNEMONIC = "mnemonic"
        private val PASSPHRASE = "passphrase"
        private val AMOUNT = "amount"
        private val AUTH_UNLOCK_WALLET = "authUnlockWallet"
        private val AUTH_SEND_XVG = "authSendXVG"
        private val WALLET_SERVICE_URL = "walletServiceURL"
        private val WALLET_ID = "walletId"
        private val WALLET_NAME = "walletName"
        private val WALLET_SECRET = "walletSecret"
        private val DEVICE_TOKEN = "deviceToken"
        private val PREFERRED_THEME = "preferredTheme"


        private var INSTANCE: PreferencesManager? = null
        lateinit private var preferences: SharedPreferences
        private var encryptedPreferences: SharedPreferences? = null

        //--------Singleton methods
        fun init(context: Context): PreferencesManager? {
            if (INSTANCE != null) {
                throw AssertionError("You already initialized an object of this type")
            } else {
                INSTANCE = PreferencesManager(context)
                return INSTANCE
            }
        }

        val instance: PreferencesManager?
            get() {
                if (INSTANCE == null) {
                    throw AssertionError("You haven't initialized an object of this type yet.")
                }
                return INSTANCE
            }

        @Throws(GeneralSecurityException::class, IOException::class)
        fun initEncryptedPreferences(context: Context) {
            if (encryptedPreferences == null) {
                val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
                encryptedPreferences = EncryptedSharedPreferences.create(
                        "com.vergecurrency.vergewallet.secrets",
                        masterKeyAlias,
                        context,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )
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

        //-------User passphrase
        var passphrase: String?
            get() = preferences.getString(PASSPHRASE, "mnemonic")
            set(passphrase) = encryptedPreferences!!.edit().putString(PASSPHRASE, passphrase).apply()

        //---------Preferred currency
        val preferredCurrency: String?
            get() = encryptedPreferences!!.getString(PREFERRED_CURRENCY, Currency("United States Dollar", "USD").currencyAsJSON)

        fun setSelectedCurrency(preferredCurrency: String) {
            encryptedPreferences!!.edit().putString(PREFERRED_CURRENCY, preferredCurrency).apply()
        }

        //---------Using Tor
        var usingTor: Boolean
            get() = encryptedPreferences!!.getBoolean(USING_TOR, false)
            set(isUsingTor) = encryptedPreferences!!.edit().putBoolean(USING_TOR, isUsingTor).apply()

        //---------Wallet amount

        var amount: Float
            get() = encryptedPreferences!!.getFloat(AMOUNT, 0f)
            set(amount) {
                var amount = amount
                if (amount < 0f) {
                    amount = 0f
                }
                encryptedPreferences!!.edit().putFloat(AMOUNT, amount).apply()
            }

        //--------walletsecret
        var walletSecret: String?
            get() = encryptedPreferences!!.getString(WALLET_SECRET, null)
            set(secret) {
                var secret = secret
                if (secret == null) {
                    secret = ""
                }
                encryptedPreferences!!.edit().putString(WALLET_SECRET, secret).apply()
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
                var id = id
                if (id == null) {
                    id = ""
                }
                encryptedPreferences!!.edit().putString(WALLET_ID, id).apply()
            }

        //--------walletname
        var walletName: String?
            get() = encryptedPreferences!!.getString(WALLET_NAME, null)
            set(name) {
                var name = name
                if (name == null) {
                    name = ""
                }
                encryptedPreferences!!.edit().putString(WALLET_NAME, name).apply()
            }


        //--------vws endpoint
        var walletServiceUrl: String?
            get() = preferences.getString(WALLET_SERVICE_URL, Constants.VWS_ENDPOINT)
            set(url) = preferences.edit().putString(WALLET_SERVICE_URL, url).apply()

        var currentTheme: String?
            get() = preferences.getString(PREFERRED_THEME, "Feather Theme")
            set(theme) = preferences.edit().putString(PREFERRED_THEME, theme).apply()

        //---------Preferred language

        var currentLanguage: String?
            get() = preferences.getString(LANGUAGE, Language("English", "en").languageAsJson)
            set(language) = preferences.edit().putString(LANGUAGE, language).apply()

        //---------First launch
        var isFirstLaunch: Boolean
            get() = preferences.getBoolean(FIRST_LAUNCH, true)
            set(firstLaunchValue) = preferences.edit().putBoolean(FIRST_LAUNCH, firstLaunchValue).apply()
    }

}
