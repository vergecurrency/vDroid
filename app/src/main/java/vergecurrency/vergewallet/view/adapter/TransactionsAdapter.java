package vergecurrency.vergewallet.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.view.ui.components.transaction.TransactionHeaderItem;
import vergecurrency.vergewallet.view.ui.components.transaction.TransactionItem;
import vergecurrency.vergewallet.view.ui.components.transaction.TransactionListItem;
import vergecurrency.vergewallet.view.ui.components.transaction.TransactionRowType;
import vergecurrency.vergewallet.service.model.Transaction;
import vergecurrency.vergewallet.service.model.TransactionFilterOption;

public class TransactionsAdapter extends ArrayAdapter<TransactionItem> {
    private ArrayList<Transaction> transactions;
    private LayoutInflater inflater;
    private boolean appendListHeader;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");


    /**
     * Transaction adapter constructor
     *
     * @param context the application context
     * @param txs     the transactions list we need to display
     */
    public TransactionsAdapter(@NonNull Context context, ArrayList<Transaction> txs, boolean appendListHeader) {
        super(context, R.layout.listview_item_transaction, new ArrayList<>());
        this.appendListHeader = appendListHeader;
        this.transactions = txs;
        this.inflater = LayoutInflater.from(context);
        super.addAll(toTransactionItemList(txs));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItem(position).getView(inflater, convertView, parent, position);
    }


    @Override
    public int getViewTypeCount() {
        return TransactionRowType.values().length;

    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getViewType();
    }


    private ArrayList<TransactionItem> toTransactionItemList(ArrayList<Transaction> txs) {
        ArrayList<TransactionItem> transactionItems = new ArrayList<>();
        Collections.sort(txs, Transaction.TimeComparatorDESC);
        txs.forEach(tx -> {
            //If tx isn't the last transaction
            if (txs.indexOf(tx) != txs.size() - 1 && appendListHeader) {
                Transaction nextTx = txs.get(txs.indexOf(tx) + 1);
                //If current and next transaction having not the same date
                if (txs.indexOf(tx) != txs.size() - 1 && nextTx != null && isSameDate(tx, nextTx) != 0) {
                    transactionItems.add(new TransactionHeaderItem(this.convertToLocalDateViaMillisecond(tx.getTime() * 1000).format(formatter)));
                }
            }
            transactionItems.add(new TransactionListItem(tx));
        });
        return transactionItems;
    }

    public void filter(String charText, TransactionFilterOption filterOption) {
        clear();
        ArrayList<Transaction> filteredTransactions = filterByOption(this.transactions, filterOption);
        this.transactions.forEach(tx -> {
            if (!this.currentTextFilterMatch(tx, charText)) {
                filteredTransactions.remove(tx);
            }
        });
        addAll(toTransactionItemList(filteredTransactions));
        notifyDataSetChanged();
    }

    private boolean currentTextFilterMatch(Transaction tx, String charText) {
        return tx.getAddress().toLowerCase().contains(charText.toLowerCase()) || Double.toString(tx.getAmount()).toLowerCase().contains(charText.toLowerCase());
    }

    private LocalDate convertToLocalDateViaMillisecond(long time) {
        return Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private ArrayList<Transaction> filterByOption(ArrayList<Transaction> txs, TransactionFilterOption filterOption) {
        switch (filterOption) {
            case RECEIVE:
                return txs.stream().filter(Transaction::isReceive).collect(Collectors.toCollection(ArrayList::new));
            case SEND:
                return txs.stream().filter(Transaction::isSend).collect(Collectors.toCollection(ArrayList::new));
            default:
                return new ArrayList<>(txs);
        }
    }

    private int isSameDate(Transaction tx1, Transaction tx2) {
        LocalDate firstDate = this.convertToLocalDateViaMillisecond(tx1.getTime() * 1000);
        LocalDate secondDate = this.convertToLocalDateViaMillisecond(tx2.getTime() * 1000);
        return firstDate.compareTo(secondDate);
    }
}