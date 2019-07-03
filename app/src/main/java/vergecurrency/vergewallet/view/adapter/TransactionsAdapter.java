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
    Context context;
    ArrayList<Transaction> transactions;
    LayoutInflater inflater;
    private boolean appendListHeader;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");


    /**
     * Transaction adapter constructor
     *
     * @param context the application context
     * @param txs     the transactions list we need to display
     */
    public TransactionsAdapter(@NonNull Context context, ArrayList<Transaction> txs, boolean appendListHeader) {
        super(context, R.layout.listview_item_transaction, new ArrayList<>());
        this.appendListHeader = appendListHeader;
        this.context = context;
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
        ArrayList<TransactionItem> transactionItems = new ArrayList();
        Collections.sort(txs, Transaction.TimeComparatorDESC);
        txs.forEach(tx -> {
            //If tx isn't the last transaction
            if (txs.indexOf(tx) != txs.size() - 1 && appendListHeader) {
                Transaction nextTx = txs.get(txs.indexOf(tx) + 1);
                //If current and next transaction having not the same date
                if (txs.indexOf(tx) != txs.size() - 1 && nextTx != null && isSameDate(tx, nextTx) != 0) {
                    transactionItems.add(new TransactionHeaderItem(this.convertToLocalDateViaMilisecond(tx.getTime() * 1000).format(formatter)));
                }
            }
            transactionItems.add(new TransactionListItem(tx));
        });
        return transactionItems;
    }

    public void filter(String charText, TransactionFilterOption filterOption) {
        clear();
        ArrayList filteredTransactions = filterByOption(this.transactions, filterOption);
        this.transactions.forEach(tx -> {
            if (!this.currentTextFilterMatch(tx, charText)) {
                filteredTransactions.remove(tx);
            }
        });
        addAll(toTransactionItemList(filteredTransactions));
        notifyDataSetChanged();
    }

    private boolean currentTextFilterMatch(Transaction tx, String charText) {
        return tx.getAddress().toLowerCase().contains(charText.toLowerCase()) || new Double(tx.getAmount()).toString().toLowerCase().contains(charText.toLowerCase());
    }

    private LocalDate convertToLocalDateViaMilisecond(long time) {
        return Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private ArrayList<Transaction> filterByOption(ArrayList<Transaction> txs, TransactionFilterOption filterOption) {
        switch (filterOption) {
            case RECEIVE:
                return new ArrayList<Transaction>(txs.stream().filter(tx -> tx.isReceive()).collect(Collectors.toList()));
            case SEND:
                return new ArrayList<Transaction>(txs.stream().filter(tx -> tx.isSend()).collect(Collectors.toList()));
            default:
                return new ArrayList<Transaction>(txs);
        }
    }

    private int isSameDate(Transaction tx1, Transaction tx2) {
        LocalDate firstDate = this.convertToLocalDateViaMilisecond(tx1.getTime() * 1000);
        LocalDate secondDate = this.convertToLocalDateViaMilisecond(tx2.getTime() * 1000);
        return firstDate.compareTo(secondDate);
    }
}