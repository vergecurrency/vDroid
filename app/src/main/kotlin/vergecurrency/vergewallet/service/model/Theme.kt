package vergecurrency.vergewallet.service.model

import com.google.gson.Gson

class Theme(iconPath: String, theme: String) {

    var theme: String? = theme
    var iconPath: String? = iconPath

    val themesAsJSON: String
        get() = Gson().toJson(this)

    companion object {

        fun getThemeFromJSON(json: String): Theme {
            return Gson().fromJson(json, Theme::class.java)
        }
    }
}
