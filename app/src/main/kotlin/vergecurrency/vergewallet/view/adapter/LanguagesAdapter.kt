package vergecurrency.vergewallet.view.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.helpers.utils.LanguagesUtils
import vergecurrency.vergewallet.helpers.utils.UIUtils
import vergecurrency.vergewallet.service.model.Language
import vergecurrency.vergewallet.service.model.PreferencesManager
import java.util.*

class LanguagesAdapter
/**
 * Transaction adapter constructor
 *
 * @param context the application context
 * @param langs   the languages list we need to display
 */
(context: Context, langs: ArrayList<Language>) : ArrayAdapter<Language>(context, R.layout.listview_item_language, langs), View.OnClickListener {
    private val currentlySelectedLanguage: Language = Language.getLanguageFromJson(PreferencesManager.currentLanguage.toString())


    override fun onClick(v: View) {
        val position = v.tag as Int
        val lang = getItem(position)

        if (v.id == R.id.listview_language_item) {
            Toast.makeText(v.context, "Language chosen : " + lang!!.name!!, Toast.LENGTH_SHORT).show()
            PreferencesManager.currentLanguage = lang.languageAsJson
            LanguagesUtils.setLocale(context, lang.languageAsJson)
            UIUtils.restartApplication(context)
        }

    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var cView = convertView

        val lang = getItem(position)
        val vh: LanguageItemViewHolder

        if (cView == null) {
            vh = LanguageItemViewHolder()
            val inflater = LayoutInflater.from(context)
            cView = inflater.inflate(R.layout.listview_item_language, parent, false)
            vh.languageName = cView!!.findViewById(R.id.listview_language_name)
            vh.languageId = cView.findViewById(R.id.listview_language_item)

            cView.tag = vh

            if (lang!!.name.equals(currentlySelectedLanguage.name)) {
                val imgView = cView.findViewById<ImageView>(R.id.list_view_settings_language_checked);
                imgView!!.setImageResource(R.drawable.icon_checked)
                DrawableCompat.setTint(imgView.drawable, ContextCompat.getColor(cView.context, UIUtils.resolveAttr(R.attr.vg_primaryLight, cView.context)))
                vh.languageName!!.setTypeface(null, Typeface.BOLD)
            }

        } else {
            vh = cView.tag as LanguageItemViewHolder
        }

        vh.languageName!!.text = lang!!.name

        vh.languageId!!.setOnClickListener(this)
        vh.languageId!!.tag = position
        // Return the completed view to render on screen
        return cView
    }

    private class LanguageItemViewHolder {
        internal var languageName: TextView? = null
        internal var languageId: LinearLayout? = null
    }
}
