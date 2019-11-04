package vergecurrency.vergewallet.helpers.utils

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import com.google.gson.GsonBuilder
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException
import vergecurrency.vergewallet.Constants
import vergecurrency.vergewallet.service.model.Language
import vergecurrency.vergewallet.service.model.PreferencesManager
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

object LanguagesUtils {

    fun loadLanguagesFromFile(c: Context): ArrayList<Language>? {
        val parser = JSONParser()
        var languages: ArrayList<Language>?
        try {
            val `is` = c.assets.open(Constants.LANGUAGES_FILE_PATH)
            val isr = InputStreamReader(`is`)
            val jsonObject = parser.parse(isr) as JSONObject
            val languagesJSON = jsonObject["languages"] as JSONArray?


            val languagesArray: Array<Language>
            languagesArray = GsonBuilder().create().fromJson(languagesJSON!!.toJSONString(), Array<Language>::class.java)
            languages = ArrayList(Arrays.asList(*languagesArray))


        } catch (e: IOException) {
            languages = null
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
            languages = null
        }

        return languages
    }

    fun onAttach(context: Context): Context {
        val lang = persistedData
        return setLocale(context, lang)
    }

    val language: String
        get() = persistedData

    fun setLocale(context: Context, lang: String): Context {
        val language = Language.getLanguageFromJson(lang).language
        persist(lang)

        return updateResources(context, language!!)
    }

    private val persistedData: String
        get() = PreferencesManager.currentLanguage!!

    private fun persist(language: String) {
        PreferencesManager.currentLanguage = language
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val configuration = context.resources.configuration
        configuration.setLocale(locale)

        return context.createConfigurationContext(configuration)
    }
}
