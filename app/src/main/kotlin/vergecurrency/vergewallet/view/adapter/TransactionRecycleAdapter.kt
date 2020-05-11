package vergecurrency.vergewallet.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.helpers.utils.MathUtils
import vergecurrency.vergewallet.helpers.utils.TransactionUtils
import vergecurrency.vergewallet.service.model.Transaction
import vergecurrency.vergewallet.service.model.TransactionFilterOption
import vergecurrency.vergewallet.view.ui.components.transaction.TransactionViewHolder
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class TransactionRecycleAdapter(private val transactions: ArrayList<Transaction>, private val appendListHeader: Boolean) :
        RecyclerView.Adapter<TransactionViewHolder>() {
    private val formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy")
    private var internalTransactions: ArrayList<Transaction> = transactions;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.listview_item_transaction, parent, false)
        Collections.sort(transactions, Transaction.Companion.TimeComparatorDESC);
        return TransactionViewHolder(view);
    }

    override fun getItemCount(): Int {
        return this.internalTransactions.size
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = internalTransactions[position]
/*
        if (position != internalTransactions.lastIndex) {
            val nextPos = position + 1;
            val nextTx = internalTransactions[nextPos]
            if (isSameDate(transaction, nextTx) != 0 && appendListHeader) {
                holder.headerName.text = this.convertToLocalDateViaMillisecond(transaction.time * 1000).format(formatter)
            }
        } else if (itemCount > 1 && isSameDate(transaction, internalTransactions[position - 1]) != 0 && appendListHeader) {
            holder.headerName.text = this.convertToLocalDateViaMillisecond(transaction.time * 1000).format(formatter)
        }
*/
        if (transaction.isSend) {
            holder.txAmount!!.text = String.format("- %s XVG", MathUtils.round(transaction.amount, 2))
            holder.txAmount!!.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.material_red_500))
            holder.txIcon!!.setImageResource(R.drawable.icon_tx_sent)
            DrawableCompat.setTint(holder.txIcon!!.drawable, ContextCompat.getColor(holder.itemView.context, R.color.material_red_500))
        } else if (transaction.isReceive) {
            holder.txAmount!!.text = String.format("+ %s XVG", MathUtils.round(transaction.amount, 2))
            holder.txAmount!!.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.material_green_500))
            holder.txIcon!!.setImageResource(R.drawable.icon_tx_received)
            DrawableCompat.setTint(holder.txIcon!!.drawable, ContextCompat.getColor(holder.itemView.context, R.color.material_green_500))
        }

        if (transaction.account == null) {
            if (transaction.isReceive) {
                holder.txAddress!!.text = holder.itemView.resources.getString(R.string.fragment_transaction_received_filter)
            } else {
                holder.txAddress!!.text = holder.itemView.resources.getString(R.string.fragment_transaction_sent_filter)
            }
        } else {
            holder.txAddress!!.text = transaction.account
        }
        holder.txDateTime!!.setText(TransactionUtils.toFormattedDate(transaction.time, holder.itemView.context))
    }


    fun filter(charText: String, filterOption: TransactionFilterOption) {
        val filteredTransactions = filterByOption(this.transactions, filterOption)
        this.transactions.forEach { tx ->
            if (!this.currentTextFilterMatch(tx, charText)) {
                filteredTransactions.remove(tx)
            }
        }

        internalTransactions = filteredTransactions;
        notifyDataSetChanged()
    }

    fun getTransaction(position: Int): Transaction {
        return internalTransactions.get(position);
    }

    @SuppressLint("DefaultLocale")
    private fun currentTextFilterMatch(tx: Transaction, charText: String): Boolean {
        return tx.account!!.toLowerCase().contains(charText.toLowerCase()) ||
                tx.address!!.toLowerCase().contains(charText.toLowerCase()) ||
                tx.amount.toString().toLowerCase().contains(charText.toLowerCase())
    }

    private fun convertToLocalDateViaMillisecond(time: Long): LocalDate {
        return Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate()
    }

    private fun filterByOption(txs: java.util.ArrayList<Transaction>, filterOption: TransactionFilterOption): java.util.ArrayList<Transaction> {
        return when (filterOption) {


            TransactionFilterOption.RECEIVE -> java.util.ArrayList(txs.filter { it.isReceive })
            TransactionFilterOption.SEND -> java.util.ArrayList(txs.filter { it.isSend })
            else -> java.util.ArrayList(txs)
        }
    }

    private fun isSameDate(tx1: Transaction, tx2: Transaction): Int {
        val firstDate = this.convertToLocalDateViaMillisecond(tx1.time * 1000)
        val secondDate = this.convertToLocalDateViaMillisecond(tx2.time * 1000)
        return firstDate.compareTo(secondDate)
    }
}