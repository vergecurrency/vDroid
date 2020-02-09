package vergecurrency.vergewallet.view.ui.components.transaction

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.helpers.utils.MathUtils
import vergecurrency.vergewallet.helpers.utils.TransactionUtils
import vergecurrency.vergewallet.helpers.utils.TransactionUtils.toFormattedDate
import vergecurrency.vergewallet.service.model.Transaction
import vergecurrency.vergewallet.view.ui.activity.TransactionDetailActivity

class TransactionListItem(private val tx: Transaction) : TransactionItem, View.OnClickListener {

    override val viewType: Int
        get() = TransactionRowType.LIST_ITEM.ordinal


    override fun onClick(v: View) {
        val intent = Intent(v.context, TransactionDetailActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        intent.putExtra(TransactionUtils.EXTRA_TRANSACTION, tx)
        v.context.startActivity(intent)
    }

    override fun getView(inflater: LayoutInflater, convertView: View?, parent: ViewGroup, position: Int): View {
        var view: View
        var vh: TransactionItemViewHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.listview_item_transaction, parent, false)
            vh = prepareViewHolder(TransactionItemViewHolder(), view);
        } else {
            view = convertView;
            vh = prepareViewHolder(convertView.tag as TransactionItemViewHolder, view);
        }
        vh.txAddress!!.tag = position
        view.tag = vh;
        view.setOnClickListener(this);

        // Return the completed view to render on screen
        return view
    }

    /**
     * Internal struct containing tx info
     */
    private class TransactionItemViewHolder {
        internal var txIcon: ImageView? = null
        internal var txAddress: TextView? = null
        internal var txDateTime: TextView? = null
        internal var txAmount: TextView? = null
    }

    private fun prepareViewHolder(vh: TransactionItemViewHolder, view: View): TransactionItemViewHolder {
        vh.txAddress = view.findViewById(R.id.listview_transaction_item_address)
        vh.txAmount = view.findViewById(R.id.listview_transaction_item_amount)
        vh.txDateTime = view.findViewById(R.id.listview_transaction_item_datetime)
        vh.txIcon = view.findViewById(R.id.listview_transaction_item_icon)

        if (tx.isSend) {
            vh.txAmount!!.text = String.format("- %s XVG", MathUtils.round(tx.amount, 2))
            vh.txAmount!!.setTextColor(ContextCompat.getColor(view.context, R.color.material_red_500))
            vh.txIcon!!.setImageResource(R.drawable.icon_tx_sent)
            DrawableCompat.setTint(vh.txIcon!!.drawable, ContextCompat.getColor(view.context, R.color.material_red_500))
        } else if (tx.isReceive) {
            vh.txAmount!!.text = String.format("+ %s XVG", MathUtils.round(tx.amount, 2))
            vh.txAmount!!.setTextColor(ContextCompat.getColor(view.context, R.color.material_green_500))
            vh.txIcon!!.setImageResource(R.drawable.icon_tx_received)
            DrawableCompat.setTint(vh.txIcon!!.drawable, ContextCompat.getColor(view.context, R.color.material_green_500))
        }

        if (tx.account == null) {
            if (tx.isReceive) {
                vh.txAddress!!.text = view.resources.getString(R.string.fragment_transaction_received_filter)
            } else {
                vh.txAddress!!.text = view.resources.getString(R.string.fragment_transaction_sent_filter)
            }
        } else {
            vh.txAddress!!.text = tx.account
        }
        vh.txDateTime!!.setText(toFormattedDate(tx.time))
        return vh;
    }

}
