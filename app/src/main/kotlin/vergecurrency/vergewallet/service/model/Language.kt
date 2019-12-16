package vergecurrency.vergewallet.service.model

import com.google.gson.Gson

class Language(name: String, language: String) {

    var language: String? = language
    var name: String? = name

    val languageAsJson: String
        get() = Gson().toJson(this)


    companion object {

        fun getLanguageFromJson(json: String): Language {
            return Gson().fromJson(json, Language::class.java)
        }
    }
}
