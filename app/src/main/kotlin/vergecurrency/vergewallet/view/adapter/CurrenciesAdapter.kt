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
import vergecurrency.vergewallet.service.model.Currency
import vergecurrency.vergewallet.service.model.PreferencesManager
import vergecurrency.vergewallet.view.ui.activity.settings.ChooseCurrencyActivity

class CurrenciesAdapter
/**
 * Transaction adapter constructor
 *
 * @param context the application context
 * @param curs    the currencies list we need to display
 */
(context: Context, curs: ArrayList<Currency>) : ArrayAdapter<Currency>(context, R.layout.listview_item_currency, curs), View.OnClickListener {

    override fun onClick(v: View) {
        val position = v.tag as Int
        val currency = getItem(position)

        if (v.id == R.id.listview_currency_item) {
            Toast.makeText(v.context, "Currency chosen : " + currency!!.name!!, Toast.LENGTH_SHORT).show()
            PreferencesManager.setSelectedCurrency(currency.currencyAsJSON)
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

        } else {
            vh = cView.tag as CurrencyItemViewHolder
        }

        vh.currencyCurrency!!.text = cur!!.currency
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
