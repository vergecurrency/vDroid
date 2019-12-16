package vergecurrency.vergewallet.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

import java.util.ArrayList

import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.helpers.utils.LanguagesUtils
import vergecurrency.vergewallet.helpers.utils.UIUtils
import vergecurrency.vergewallet.service.model.Language
import vergecurrency.vergewallet.service.model.PreferencesManager

class LanguagesAdapter
/**
 * Transaction adapter constructor
 *
 * @param context the application context
 * @param langs   the languages list we need to display
 */
(context: Context, langs: ArrayList<Language>) : ArrayAdapter<Language>(context, R.layout.listview_item_language, langs), View.OnClickListener {

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
        var convertView = convertView

        val lang = getItem(position)
        val vh: LanguageItemViewHolder

        if (convertView == null) {
            vh = LanguageItemViewHolder()
            val inflater = LayoutInflater.from(context)
            convertView = inflater.inflate(R.layout.listview_item_language, parent, false)
            vh.languageName = convertView!!.findViewById(R.id.listview_language_name)
            vh.languageId = convertView.findViewById(R.id.listview_language_item)

            convertView.tag = vh

        } else {
            vh = convertView.tag as LanguageItemViewHolder
        }

        vh.languageName!!.text = lang!!.name

        vh.languageId!!.setOnClickListener(this)
        vh.languageId!!.tag = position
        // Return the completed view to render on screen
        return convertView
    }

    private class LanguageItemViewHolder {
        internal var languageName: TextView? = null
        internal var languageId: LinearLayout? = null
    }
}
