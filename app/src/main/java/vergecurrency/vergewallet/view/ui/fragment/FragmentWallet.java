package vergecurrency.vergewallet.view.ui.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.service.model.Preferences;
import vergecurrency.vergewallet.service.model.Currency;
import vergecurrency.vergewallet.view.ui.fragment.walletpageviews.FragmentStatisticsPageView;
import vergecurrency.vergewallet.view.ui.fragment.walletpageviews.FragmentTransactionsPageView;
import vergecurrency.vergewallet.view.ui.fragment.walletpageviews.FragmentChartsPageView;


public class FragmentWallet extends Fragment {

    public View rootView;
    private String currencyCode;
    private Preferences pm;
    private ViewPager pager;
    private TextView balanceLabel;
    private TextView balanceAmount;
    private TextView changeLabel;
    private TextView changeAmount;


    public FragmentWallet() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        pm = new Preferences(getContext());
        currencyCode  = Currency.getCurrencyFromJson(pm.getSelectedCurrency()).getCurrency();

        // Inflate the layout for this fragment

        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_wallet, container, false);

            //Instantiate the ViewPager to have the fragments into the viewpager layout
            pager = rootView.findViewById(R.id.wallet_cards_viewpager);
			balanceLabel = rootView.findViewById(R.id.wallet_card_balance_label);
			balanceAmount= rootView.findViewById(R.id.wallet_card_balance);
			changeLabel = rootView.findViewById(R.id.wallet_card_change_label);
			changeAmount = rootView.findViewById(R.id.wallet_card_change);


            initComponents();
        }
        return rootView;
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {

            switch (pos) {

                case 0:
                   return FragmentTransactionsPageView.instantiate(getContext(), FragmentTransactionsPageView.class.getName());
                case 1:
                    return FragmentStatisticsPageView.instantiate(getContext(),FragmentStatisticsPageView.class.getName());
                case 2:
                    return FragmentChartsPageView.instantiate(getContext(), FragmentChartsPageView.class.getName());
                default:
                   return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }


    private void initComponents() {
        pager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        balanceLabel.setText(String.format("%s BALANCE", currencyCode));
        changeLabel.setText(String.format("%s/XVG", currencyCode));
        balanceAmount.setText(String.format("%s 132.15", currencyCode));
        changeAmount.setText(String.format("%s 0.017", currencyCode));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}

