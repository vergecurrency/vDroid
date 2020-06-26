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
import vergecurrency.vergewallet.helpers.utils.UIUtils
import vergecurrency.vergewallet.service.model.Currency
import vergecurrency.vergewallet.service.model.EncryptedPreferencesManager
import vergecurrency.vergewallet.view.ui.activity.settings.ChooseCurrencyActivity
import java.util.*

class CurrenciesAdapter
/**
 * Transaction adapter constructor
 *
 * @param context the application context
 * @param curs    the currencies list we need to display
 */
(context: Context, curs: ArrayList<Currency>) : ArrayAdapter<Currency>(context, R.layout.listview_item_currency, curs), View.OnClickListener {
    private val currentlySelectedCurrency: Currency = Currency.getCurrencyFromJson(EncryptedPreferencesManager.preferredCurrency!!)


    override fun onClick(v: View) {
        val position = v.tag as Int
        val currency = getItem(position)

        if (v.id == R.id.listview_currency_item) {
            Toast.makeText(v.context, "Currency chosen : " + currency!!.name!!, Toast.LENGTH_SHORT).show()
            EncryptedPreferencesManager.preferredCurrency = currency.currencyAsJSON.toByteArray()

        }

        (context as ChooseCurrencyActivity).finish()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var cView = convertView

        val cur = getItem(position)
        val vh: CurrencyItemViewHolder

        if (cView == null) {
            vh = CurrencyItemViewHolder()
            val inflater = LayoutInflater.from(context)
            cView = inflater.inflate(R.layout.listview_item_currency, parent, false)
            vh.currencyId = cView!!.findViewById(R.id.listview_currency_item)
            vh.currencyCurrency = cView.findViewById(R.id.listview_currency_currency)
            vh.currencyName = cView.findViewById(R.id.listview_currency_name)
            cView.tag = vh
            if (cur!!.name.equals(currentlySelectedCurrency.name) && cur.currency.toString().equals(currentlySelectedCurrency.currency)) {
                val imgView = cView.findViewById<ImageView>(R.id.list_view_settings_currency_checked);
                imgView!!.setImageResource(R.drawable.icon_checked)
                DrawableCompat.setTint(imgView.drawable, ContextCompat.getColor(cView.context, UIUtils.resolveAttr(R.attr.vg_primaryLight, cView.context)))
                vh.currencyCurrency!!.setTypeface(null, Typeface.BOLD)
                vh.currencyName!!.setTypeface(null, Typeface.BOLD)
            }
        } else {
            vh = cView.tag as CurrencyItemViewHolder
        }


        vh.currencyCurrency!!.text = cur!!.currency.toString()
        vh.currencyName!!.text = cur.name

        vh.currencyId!!.setOnClickListener(this)
        vh.currencyId!!.tag = position

        // Return the completed view to render on screen
        return cView
    }

    private class CurrencyItemViewHolder {
        internal var currencyCurrency: TextView? = null
        internal var currencyName: TextView? = null
        internal var currencyId: LinearLayout? = null
    }
}
