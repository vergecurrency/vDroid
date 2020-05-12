package vergecurrency.vergewallet.view.ui.components.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vergecurrency.vergewallet.R

class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    internal var txAddress: TextView? = view.findViewById(R.id.listview_transaction_item_address)
    internal var txAmount: TextView? = view.findViewById(R.id.listview_transaction_item_amount)
    internal var txDateTime: TextView? = view.findViewById(R.id.listview_transaction_item_datetime)
    internal var txIcon: ImageView? = view.findViewById(R.id.listview_transaction_item_icon)
}