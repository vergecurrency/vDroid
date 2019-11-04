package vergecurrency.vergewallet.helpers.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.util.TypedValue

import vergecurrency.vergewallet.R

object UIUtils {

    fun setTheme(theme: String, context: Context, recreateActivity: Boolean) {
        if (theme == "Feather Theme") {
            context.setTheme(R.style.FeatherTheme)
        } else if (theme == "Moon Mode") {
            context.setTheme(R.style.MoonMode)
        }

        if (recreateActivity) {
            (context as Activity).recreate()
        }
    }

    fun restartApplication(c: Context) {
        val i = c.packageManager
                .getLaunchIntentForPackage(c.packageName)
        i!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        c.startActivity(i)
    }

    fun resolveAttr(attr: Int, c: Context): Int {
        val typedValue = TypedValue()
        val theme = c.theme
        theme.resolveAttribute(attr, typedValue, true)

        return typedValue.resourceId
    }
}
