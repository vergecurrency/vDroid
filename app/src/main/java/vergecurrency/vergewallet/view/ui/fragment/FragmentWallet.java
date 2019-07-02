package vergecurrency.vergewallet.view.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.view.adapter.WalletAdapter;
import vergecurrency.vergewallet.view.base.BaseFragment;
import vergecurrency.vergewallet.viewmodel.WalletFragmentViewModel;

public class FragmentWallet extends BaseFragment {

	private View rootView;
	private String currencyCode;
	private WalletFragmentViewModel mViewModel;

	public FragmentWallet() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mViewModel = ViewModelProviders.of(this).get(WalletFragmentViewModel.class);

		currencyCode = mViewModel.getCurrencyCode();

		// Inflate the layout for this fragment

		if (rootView == null) {
			rootView = inflater.inflate(R.layout.fragment_wallet, container, false);

			initComponents();
		}
		return rootView;
	}

	private void initComponents() {

		ViewPager pager = rootView.findViewById(R.id.wallet_cards_viewpager);
		pager.setAdapter(new WalletAdapter(getChildFragmentManager(), getContext()));

		TextView balanceLabel = rootView.findViewById(R.id.wallet_card_balance_label);
		balanceLabel.setText(String.format("%s BALANCE", currencyCode));

		TextView balanceXVG = rootView.findViewById(R.id.wallet_card_verge_balance);
		balanceXVG.setText(String.format("XVG %o", mViewModel.getBalance()));

		TextView balanceFiat = rootView.findViewById(R.id.wallet_card_fiat_balance);
		balanceFiat.setText(String.format("%s %o", currencyCode, mViewModel.getBalance()));

		TextView changeLabel = rootView.findViewById(R.id.wallet_card_change_label);
		changeLabel.setText(String.format("%s/XVG", currencyCode));

		TextView changeAmount = rootView.findViewById(R.id.wallet_card_change);
		changeAmount.setText(String.format("%s 0.017", currencyCode));
	}

}

