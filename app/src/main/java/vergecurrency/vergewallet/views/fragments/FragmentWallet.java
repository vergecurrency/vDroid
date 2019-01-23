package vergecurrency.vergewallet.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.views.fragments.beans.ItemData;
import vergecurrency.vergewallet.views.fragments.walletcards.FragmentTransactionsCard;
import vergecurrency.vergewallet.views.fragments.walletcards.FragmentWalletCard;


public class FragmentWallet extends Fragment {

    public View rootView;

    public FragmentWallet() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_wallet, container, false);

            //Instantiate the ViewPager to have the fragments into the viewpager layout
            ViewPager pager = (ViewPager) rootView.findViewById(R.id.wallet_cards_viewpager);
            pager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));


            //Fill the different cards with their contents
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
                   //return FragmentWalletCard.newInstance(new ItemData("Recent transactions", R.drawable.icon_transactions), R.drawable.icon_transactions);
                   return FragmentTransactions.instantiate(getContext(),FragmentTransactionsCard.class.getName());
                case 1:

                    return FragmentWalletCard.newInstance( new ItemData("Price Statistics", R.drawable.icon_stat_increase, null),R.drawable.icon_stat_increase);
                case 2:

                    return FragmentWalletCard.newInstance(new ItemData("History Chart", R.drawable.icon_chart, null),R.drawable.icon_chart);
                default:
                   return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}

