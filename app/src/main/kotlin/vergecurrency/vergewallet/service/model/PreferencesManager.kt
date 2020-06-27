package vergecurrency.vergewallet.service.model

import android.content.Context
import android.content.SharedPreferences
import vergecurrency.vergewallet.Constants


/**
 * Warning fella : this class is a singleton object if you are wondering why I did that shitshow ;)
 */
class PreferencesManager private constructor(context: Context) {

    init {
        preferences = context.getSharedPreferences("com.vergecurrency.vergewallet", Context.MODE_PRIVATE)
    }

    companion object {
        private const val FIRST_LAUNCH = "firstlaunch"
        private const val LANGUAGE = "language"
        private const val WALLET_SERVICE_URL = "walletServiceURL"
        private const val PREFERRED_THEME = "preferredTheme"
        private const val PREFERRED_CURRENCY = "preferredCurrency"
        private const val USING_TOR = "usingTor"


        private var INSTANCE: PreferencesManager? = null
        lateinit var preferences: SharedPreferences

        //--------Singleton methods
        fun getInstance(context: Context): PreferencesManager? {
            if (INSTANCE != null) {
                throw AssertionError("You already initialized an object of this type")
            } else {
                INSTANCE = PreferencesManager(context)
                return INSTANCE
            }
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

        //---------Preferred currency
        var preferredCurrency: String?
            get() = preferences.getString(PREFERRED_CURRENCY, Currency("United States Dollar", "USD").currencyAsJSON)!!
            set(preferredCurrency) = preferences.edit().putString(PREFERRED_CURRENCY, preferredCurrency!!).apply()

        //---------Using Tor
        var usingTor: Boolean
            get() = preferences.getBoolean(USING_TOR, false)
            set(isUsingTor) = preferences.edit().putBoolean(USING_TOR, false).apply()
    }

}
