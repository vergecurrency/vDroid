package vergecurrency.vergewallet.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.service.model.Transaction
import vergecurrency.vergewallet.service.model.TransactionFilterOption
import vergecurrency.vergewallet.view.ui.components.transaction.TransactionHeaderItem
import vergecurrency.vergewallet.view.ui.components.transaction.TransactionItem
import vergecurrency.vergewallet.view.ui.components.transaction.TransactionListItem
import vergecurrency.vergewallet.view.ui.components.transaction.TransactionRowType
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.function.Supplier
import java.util.stream.Collectors

/**
 * Transaction adapter constructor
 *
 * @param context the application context
 * @param txs     the transactions list we need to display
 */
class TransactionsAdapter(context: Context, private val transactions: ArrayList<Transaction>, private val appendListHeader: Boolean) : ArrayAdapter<TransactionItem>(context, R.layout.listview_item_transaction, ArrayList()) {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy")


    init {
        super.addAll(toTransactionItemList(transactions))
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getItem(position)!!.getView(inflater, convertView, parent, position)
    }


    override fun getViewTypeCount(): Int {
        return TransactionRowType.values().size

    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)!!.viewType
    }


    private fun toTransactionItemList(txs: ArrayList<Transaction>): ArrayList<TransactionItem> {
        val transactionItems = ArrayList<TransactionItem>()
        txs.sort()
        //Collections.sort(txs, Transaction.Companion.getTimeComparatorDESC());
        txs.forEach { tx ->
            //If tx isn't the last transaction
            if (txs.indexOf(tx) != txs.size - 1 && appendListHeader) {
                val nextTx = txs[txs.indexOf(tx) + 1]
                //If current and next transaction having not the same date
                if (txs.indexOf(tx) != txs.size - 1 && isSameDate(tx, nextTx) != 0) {
                    transactionItems.add(TransactionHeaderItem(this.convertToLocalDateViaMillisecond(tx.time * 1000).format(formatter)))
                }
            }
            transactionItems.add(TransactionListItem(tx))
        }
        return transactionItems
    }

    fun filter(charText: String, filterOption: TransactionFilterOption) {
        clear()
        val filteredTransactions = filterByOption(this.transactions, filterOption)
        this.transactions.forEach { tx ->
            if (!this.currentTextFilterMatch(tx, charText)) {
                filteredTransactions.remove(tx)
            }
        }
        addAll(toTransactionItemList(filteredTransactions))
        notifyDataSetChanged()
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

    private fun filterByOption(txs: ArrayList<Transaction>, filterOption: TransactionFilterOption) :ArrayList<Transaction> {
        return when (filterOption) {


            TransactionFilterOption.RECEIVE -> ArrayList(txs.filter{ it.isReceive })
            TransactionFilterOption.SEND ->ArrayList(txs.filter{ it.isSend })
            else -> ArrayList(txs)
        }
    }

    private fun isSameDate(tx1: Transaction, tx2: Transaction): Int {
        val firstDate = this.convertToLocalDateViaMillisecond(tx1.time * 1000)
        val secondDate = this.convertToLocalDateViaMillisecond(tx2.time * 1000)
        return firstDate.compareTo(secondDate)
    }
}