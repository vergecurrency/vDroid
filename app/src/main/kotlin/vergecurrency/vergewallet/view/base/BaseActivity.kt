package vergecurrency.vergewallet.view.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import vergecurrency.vergewallet.helpers.utils.LanguagesUtils
import vergecurrency.vergewallet.helpers.utils.UIUtils
import vergecurrency.vergewallet.service.model.PreferencesManager

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        UIUtils.setTheme(PreferencesManager.currentTheme!!, this, false)
        super.onCreate(savedInstanceState)
    }

    override fun attachBaseContext(base: Context) {

        super.attachBaseContext(updateBaseContextLocale(base))
    }

    private fun updateBaseContextLocale(context: Context): Context {
        return LanguagesUtils.setLocale(context, PreferencesManager.currentLanguage!!)
    }
}
