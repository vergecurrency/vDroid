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

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.helpers.transaction.TransactionHeaderItem;
import vergecurrency.vergewallet.helpers.transaction.TransactionItem;
import vergecurrency.vergewallet.helpers.transaction.TransactionListItem;
import vergecurrency.vergewallet.helpers.transaction.TransactionRowType;
import vergecurrency.vergewallet.service.model.Transaction;

public class TransactionsAdapter extends ArrayAdapter<TransactionItem> {
    Context context;
    ArrayList<Transaction> transactions;
    LayoutInflater inflater;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");


    /**
     * Transaction adapter constructor
     *
     * @param context the application context
     * @param txs     the transactions list we need to display
     */
    public TransactionsAdapter(@NonNull Context context, ArrayList<Transaction> txs) {
        super(context, R.layout.listview_item_transaction, new ArrayList<>());
        super.addAll(toTransactionListItem(txs));
        this.context = context;
        this.transactions = txs;
        this.inflater = LayoutInflater.from(context);
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


    private ArrayList<TransactionItem> toTransactionListItem(ArrayList<Transaction> txs) {
        ArrayList<TransactionItem> transactionItems = new ArrayList();
        Collections.sort(txs, Transaction.TimeComparatorDESC);
        txs.forEach(tx -> {
            if (txs.indexOf(tx) != txs.size() - 1) {
                Transaction tx2 = txs.get(txs.indexOf(tx) + 1);
                if (tx2 != null && isSameDate(tx, tx2) != 0) {
                    transactionItems.add(new TransactionHeaderItem(this.convertToLocalDateViaMilisecond(tx.getTime() * 1000).format(formatter)));
                }
            }
            transactionItems.add(new TransactionListItem(tx));
        });
        return transactionItems;
    }

    public void filter(String charText) {
        this.clear();
        ArrayList filteredTransactions = new ArrayList<Transaction>();
        this.transactions.forEach(tx -> {
            if(this.filterMatch(tx, charText)){
             filteredTransactions.add(tx);
            }
        });
        addAll(toTransactionListItem(filteredTransactions));
        notifyDataSetChanged();
    }

    private boolean filterMatch(Transaction tx, String charText){
        return tx.getAddress().toLowerCase().contains(charText.toLowerCase()) || new Double(tx.getAmount()).toString().toLowerCase().contains(charText.toLowerCase());
    }


    public LocalDate convertToLocalDateViaMilisecond(long time) {
        return Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private int isSameDate(Transaction tx1, Transaction tx2) {
        LocalDate firstDate = this.convertToLocalDateViaMilisecond(tx1.getTime() * 1000);
        LocalDate secondDate = this.convertToLocalDateViaMilisecond(tx2.getTime() * 1000);
        return firstDate.compareTo(secondDate);
    }
}