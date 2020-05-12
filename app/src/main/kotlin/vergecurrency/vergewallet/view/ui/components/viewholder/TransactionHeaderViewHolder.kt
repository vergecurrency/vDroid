package vergecurrency.vergewallet.view.ui.components.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vergecurrency.vergewallet.R


class TransactionHeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    internal var headerName: TextView = view.findViewById<TextView>(R.id.listview_transactions_header_title)
}