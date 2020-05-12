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
import vergecurrency.vergewallet.view.ui.components.viewholder.TransactionHeaderViewHolder
import vergecurrency.vergewallet.view.ui.components.viewholder.TransactionViewHolder
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class TransactionRecycleAdapter(private val transactions: ArrayList<Transaction>, private val appendListHeader: Boolean) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy")
    private var data: ArrayList<Any> = initializeStructure(transactions);

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ViewType.TX.ordinal) {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.listview_item_transaction, parent, false)
            return TransactionViewHolder(view);
        } else if (viewType == ViewType.HEADER.ordinal) {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.listview_item_transactions_header, parent, false)
            return TransactionHeaderViewHolder(view);
        }
        throw RuntimeException();
    }

    override fun getItemCount(): Int {
        return this.data.size
    }

    fun getTransactionCount(): Int {
        return this.data.filter { it is Transaction }.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val obj: Any = data[position];
        if (obj is String && holder is TransactionHeaderViewHolder) {
            holder.headerName.text = obj
        } else if (obj is Transaction && holder is TransactionViewHolder) {
            if (obj.isSend) {
                holder.txAmount!!.text = String.format("- %s XVG", MathUtils.round(obj.amount, 2))
                holder.txAmount!!.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.material_red_500))
                holder.txIcon!!.setImageResource(R.drawable.icon_tx_sent)
                DrawableCompat.setTint(holder.txIcon!!.drawable, ContextCompat.getColor(holder.itemView.context, R.color.material_red_500))
            } else if (obj.isReceive) {
                holder.txAmount!!.text = String.format("+ %s XVG", MathUtils.round(obj.amount, 2))
                holder.txAmount!!.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.material_green_500))
                holder.txIcon!!.setImageResource(R.drawable.icon_tx_received)
                DrawableCompat.setTint(holder.txIcon!!.drawable, ContextCompat.getColor(holder.itemView.context, R.color.material_green_500))
            }

            if (obj.account == null) {
                if (obj.isReceive) {
                    holder.txAddress!!.text = holder.itemView.resources.getString(R.string.fragment_transaction_received_filter)
                } else {
                    holder.txAddress!!.text = holder.itemView.resources.getString(R.string.fragment_transaction_sent_filter)
                }
            } else {
                holder.txAddress!!.text = obj.account
            }
            holder.txDateTime!!.setText(TransactionUtils.toFormattedDate(obj.time, holder.itemView.context))
        }

    }

    override fun getItemViewType(position: Int): Int {
        if (this.data[position] is Transaction) {
            return ViewType.TX.ordinal;
        } else {
            return ViewType.HEADER.ordinal;
        }
    }


    fun filter(charText: String, filterOption: TransactionFilterOption) {
        val filteredTransactions = filterByOption(this.transactions, filterOption)
        this.transactions.forEach { tx ->
            if (!this.currentTextFilterMatch(tx, charText)) {
                filteredTransactions.remove(tx)
            }
        }

        data = initializeStructure(filteredTransactions);
        notifyDataSetChanged()
    }

    fun getObject(position: Int): Any {
        return this.data[position];
    }

    @SuppressLint("DefaultLocale")
    private fun currentTextFilterMatch(tx: Any, charText: String): Boolean {
        if (tx is Transaction) {
            return tx.account!!.toLowerCase().contains(charText.toLowerCase()) ||
                    tx.address!!.toLowerCase().contains(charText.toLowerCase()) ||
                    tx.amount.toString().toLowerCase().contains(charText.toLowerCase())
        }
        return false;
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

    private fun initializeStructure(transactions: ArrayList<Transaction>): ArrayList<Any> {
        Collections.sort(transactions, Transaction.Companion.TimeComparatorDESC);
        val txs: ArrayList<Any> = ArrayList();

        for (transaction in transactions) {
            val position = transactions.indexOf(transaction);
            if (position != 0 && appendListHeader) {
                val previousTx: Transaction = transactions[position - 1]
                if (isSameDate(transaction, previousTx) != 0) {
                    txs.add(this.convertToLocalDateViaMillisecond(transaction.time * 1000).format(formatter));
                }
            } else if (appendListHeader) {
                txs.add(this.convertToLocalDateViaMillisecond(transaction.time * 1000).format(formatter));
            }
            txs.add(transaction)
        }

        return txs;
    }

    enum class ViewType {
        HEADER, TX;
    }
}