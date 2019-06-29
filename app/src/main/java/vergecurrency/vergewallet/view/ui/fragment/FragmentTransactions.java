package vergecurrency.vergewallet.view.ui.fragment;

import android.os.Bundle;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.helpers.transaction.TransactionItem;
import vergecurrency.vergewallet.service.model.Transaction;
import vergecurrency.vergewallet.service.model.TransactionSortOrder;
import vergecurrency.vergewallet.view.adapter.TransactionsAdapter;
import vergecurrency.vergewallet.viewmodel.TransactionsViewModel;

public class FragmentTransactions extends Fragment implements SearchView.OnQueryTextListener {
    private TransactionsAdapter txa ;

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
        };
		return rootView;
	}


	@Override
	public boolean onQueryTextSubmit(String query) {
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		txa.filter(newText);
		return false;
	}
}
