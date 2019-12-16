package vergecurrency.vergewallet.view.ui.components.transaction

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import vergecurrency.vergewallet.R

class TransactionHeaderItem(private val name: String) : TransactionItem {
    override val viewType: Int
        get() =TransactionRowType.HEADER_ITEM.ordinal

    override fun getView(inflater: LayoutInflater, convertView: View?, parent: ViewGroup, position: Int): View {
        val view: View

        if (convertView == null) {
            view = inflater.inflate(R.layout.listview_item_transactions_header, null)
        } else {
            view = convertView
        }
        val txv = view.findViewById<TextView>(R.id.listview_transactions_header_title)
        txv.text = name
        return view
    }

}
