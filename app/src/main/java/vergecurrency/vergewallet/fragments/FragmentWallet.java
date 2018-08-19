package vergecurrency.vergewallet.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.fragments.beans.ItemData;


public class FragmentWallet extends Fragment {


    public FragmentWallet() {
        // Required empty public constructor
    }

    public void fillCard(View rootView, int cardID, ItemData data, int backgroundId) {

        //get the card view from the root view
        View card = rootView.findViewById(cardID);

        //Get current metrics to let the card adapt to its parent width in a scale of one -> still has to be finished
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        card.setLayoutParams(new LinearLayout.LayoutParams(metrics.widthPixels, ViewGroup.LayoutParams.MATCH_PARENT));

        //Get the card label and set its data
        View cardLabel = card.findViewById(R.id.wallet_card_label);
        TextView mTitle = cardLabel.findViewById(R.id.listview_item_title);
        ImageView mIcon = cardLabel.findViewById(R.id.listview_item_icon);
        mTitle.setText(data.getTitle());
        mIcon.setBackgroundResource(data.getImageUrl());

        //Get the card background and set it
        ImageView background = card.findViewById(R.id.wallet_card_background);
        background.setImageResource(backgroundId);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_wallet, container, false);

        //Fill the different cards with their contents
        fillCard(rootView,R.id.wallet_card_recent_transactions, new ItemData("Recent transactions", R.drawable.ic_transactions_black_24dp),R.drawable.ic_transactions_black_24dp);
        fillCard(rootView,R.id.wallet_card_price_statistics, new ItemData("Price Statistics", R.drawable.ic_icons8_increase),R.drawable.ic_icons8_increase);
        fillCard(rootView,R.id.wallet_card_history_chart, new ItemData("History Chart", R.drawable.ic_icons8_line_chart),R.drawable.ic_icons8_line_chart);



        return rootView;
    }

}

