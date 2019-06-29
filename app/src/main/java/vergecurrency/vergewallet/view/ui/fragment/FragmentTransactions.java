package vergecurrency.vergewallet.view.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.service.model.TransactionFilterOption;
import vergecurrency.vergewallet.view.adapter.TransactionsAdapter;
import vergecurrency.vergewallet.viewmodel.TransactionsViewModel;

public class FragmentTransactions extends Fragment implements SearchView.OnQueryTextListener, RadioGroup.OnCheckedChangeListener {
    private TransactionsAdapter txa;
    private TransactionFilterOption option = TransactionFilterOption.ALL;
    private String currentText = "";

    public FragmentTransactions() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TransactionsViewModel mViewModel = ViewModelProviders.of(this).get(TransactionsViewModel.class);

        View rootView;

        // Inflate the layout for this fragment
        if (mViewModel.getBalance() == 0)
            rootView = inflater.inflate(R.layout.fragment_notransactions, container, false);
        else {
            rootView = inflater.inflate(R.layout.fragment_transactions, container, false);
            ListView transactionList = rootView.findViewById(R.id.transactions_listview);
            transactionList.setDivider(null);
            txa = new TransactionsAdapter(this.getContext(), mViewModel.getTransactionsList());
            transactionList.setAdapter(txa);
            SearchView transactionSearchView = rootView.findViewById(R.id.search);
            transactionSearchView.setOnQueryTextListener(this);
            RadioGroup radioGroup = rootView.findViewById(R.id.transaction_radio_group);
            radioGroup.setOnCheckedChangeListener(this);
        }
        ;
        return rootView;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        currentText = newText;
        txa.filter(currentText, option);
        return false;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId) {
            case R.id.transactions_radio_all:
                option = TransactionFilterOption.ALL;
                txa.filter(currentText, option);
                break;
            case R.id.transactions_radio_send:
                option = TransactionFilterOption.SEND;
                txa.filter(currentText, option);
                break;
            case R.id.transactions_radio_receive:
                option = TransactionFilterOption.RECEIVE;
                txa.filter(currentText, option);
                break;
        }
    }
}
