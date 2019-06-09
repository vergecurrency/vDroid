package vergecurrency.vergewallet.view.ui.fragment.walletpageviews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.view.adapter.TransactionsAdapter;
import vergecurrency.vergewallet.viewmodel.TransactionsViewModel;

public class FragmentTransactionsPageView extends Fragment {

	public FragmentTransactionsPageView() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TransactionsViewModel mViewModel = ViewModelProviders.of(this).get(TransactionsViewModel.class);

		View rootView;

		// Inflate the layout for this fragment
		if (mViewModel.getBalance() <= 0)
			rootView = inflater.inflate(R.layout.fragment_notransactions, container, false);
		else {
			rootView = inflater.inflate(R.layout.fragment_pageview_transactions, container, false);
			ListView transactionList = rootView.findViewById(R.id.transactions_listview);
			transactionList.setAdapter(new TransactionsAdapter(this.getContext(), mViewModel.getTransactionsList()));
		}

		return rootView;
	}

}
