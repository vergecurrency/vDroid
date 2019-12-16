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
import vergecurrency.vergewallet.helpers.utils.UIUtils
import vergecurrency.vergewallet.service.model.PreferencesManager

class ThemesAdapter
/**
 * Transaction adapter constructor
 *
 * @param context the application context
 * @param themes  the themes list we need to display
 */
(context: Context, themes: ArrayList<String>) : ArrayAdapter<String>(context, R.layout.listview_item_theme, themes), View.OnClickListener {

    override fun onClick(v: View) {
        val position = v.tag as Int
        val theme = getItem(position)

        if (v.id == R.id.listview_theme_item) {
            Toast.makeText(v.context, "Theme switched to " + theme!!, Toast.LENGTH_SHORT).show()
            PreferencesManager.currentTheme = theme

            UIUtils.setTheme(theme, context, true)
            UIUtils.restartApplication(context)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        val theme = getItem(position)
        val vh: ThemeItemViewHolder

        if (convertView == null) {
            vh = ThemeItemViewHolder()
            val inflater = LayoutInflater.from(context)
            convertView = inflater.inflate(R.layout.listview_item_theme, parent, false)
            vh.themeName = convertView!!.findViewById(R.id.listview_theme_name)
            vh.themeId = convertView.findViewById(R.id.listview_theme_item)

            convertView.tag = vh

        } else {
            vh = convertView.tag as ThemeItemViewHolder
        }

        vh.themeName!!.text = theme

        vh.themeId!!.setOnClickListener(this)
        vh.themeId!!.tag = position
        // Return the completed view to render on screen
        return convertView
    }

    private class ThemeItemViewHolder {
        internal var themeName: TextView? = null
        internal var themeId: LinearLayout? = null
    }
}
