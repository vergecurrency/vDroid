package vergecurrency.vergewallet.view.ui.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.service.model.PreferencesManager;
import vergecurrency.vergewallet.service.model.Currency;
import vergecurrency.vergewallet.view.adapter.WalletAdapter;
import vergecurrency.vergewallet.view.ui.fragment.walletpageviews.FragmentStatisticsPageView;
import vergecurrency.vergewallet.view.ui.fragment.walletpageviews.FragmentTransactionsPageView;
import vergecurrency.vergewallet.view.ui.fragment.walletpageviews.FragmentChartsPageView;
import vergecurrency.vergewallet.viewmodel.WalletFragmentViewModel;


public class FragmentWallet extends Fragment {

    private View rootView;
    private String currencyCode;

	public FragmentWallet() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		WalletFragmentViewModel mViewModel = ViewModelProviders.of(this).get(WalletFragmentViewModel.class);

        currencyCode  = mViewModel.getCurrencyCode();

        // Inflate the layout for this fragment

        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_wallet, container, false);

            initComponents();
        }
        return rootView;
    }

    private void initComponents() {

		ViewPager pager = rootView.findViewById(R.id.wallet_cards_viewpager);
		pager.setAdapter(new WalletAdapter(getChildFragmentManager(),getContext()));

		TextView balanceLabel = rootView.findViewById(R.id.wallet_card_balance_label);
		balanceLabel.setText(String.format("%s BALANCE", currencyCode));

		TextView balanceAmount = rootView.findViewById(R.id.wallet_card_balance);
		balanceAmount.setText(String.format("%s 132.15", currencyCode));

		TextView changeLabel = rootView.findViewById(R.id.wallet_card_change_label);
		changeLabel.setText(String.format("%s/XVG", currencyCode));

		TextView changeAmount = rootView.findViewById(R.id.wallet_card_change);
        changeAmount.setText(String.format("%s 0.017", currencyCode));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}

