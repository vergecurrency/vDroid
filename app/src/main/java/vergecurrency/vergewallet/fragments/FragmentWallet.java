package vergecurrency.vergewallet.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.fragments.beans.ItemData;
import vergecurrency.vergewallet.fragments.walletcards.FragmentWalletCard;


public class FragmentWallet extends Fragment {


    public FragmentWallet() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_wallet, container, false);

        ViewPager pager = (ViewPager) rootView.findViewById(R.id.wallet_cards_viewpager);
        pager.setAdapter(new MyPagerAdapter(getActivity().getSupportFragmentManager()));



        //Fill the different cards with their contents
      /*fillCard(rootView,R.id.wallet_card_price_statistics, new ItemData("Price Statistics", R.drawable.ic_icons8_increase),R.drawable.ic_icons8_increase);
      fillCard(rootView,R.id.wallet_card_history_chart, new ItemData("History Chart", R.drawable.ic_icons8_line_chart),R.drawable.ic_icons8_line_chart); */

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
                    FragmentWalletCard fwc1  = new FragmentWalletCard();
                    fwc1.fillCard(new ItemData("Recent transactions", R.drawable.ic_transactions_black_24dp), R.drawable.ic_transactions_black_24dp);
                    return fwc1;
                case 1:
                    FragmentWalletCard fwc2 = new FragmentWalletCard();
                    fwc2.fillCard( new ItemData("Price Statistics", R.drawable.ic_icons8_increase),R.drawable.ic_icons8_increase);
                    return fwc2;
                case 2:
                    FragmentWalletCard fwc3= new FragmentWalletCard();
                    fwc3.fillCard(new ItemData("History Chart", R.drawable.ic_icons8_line_chart),R.drawable.ic_icons8_line_chart);
                    return fwc3;
                default:
                    return FragmentWalletCard.instantiate(getContext(),"Something");
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}

