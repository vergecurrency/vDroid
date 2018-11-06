package vergecurrency.vergewallet.views.fragments.walletcards;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import vergecurrency.vergewallet.R;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import vergecurrency.vergewallet.views.fragments.FragmentTransactions;
import vergecurrency.vergewallet.views.fragments.beans.ItemData;

public class FragmentWalletCard extends Fragment{

    public FragmentWalletCard() {

    }

    public static FragmentWalletCard newInstance(ItemData data, int backgroundId) {

        Bundle args = new Bundle();

        FragmentWalletCard fragment = new FragmentWalletCard();
        fragment.setArguments(args);
        fragment.data = data;
        fragment.backgroundId = backgroundId;

        return fragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //get the card view from the root view
        //Get the card label and set its data
        View cardLabel = getView().findViewById(R.id.wallet_card_label);
        TextView mTitle = cardLabel.findViewById(R.id.listview_item_title);
        ImageView mIcon = cardLabel.findViewById(R.id.listview_item_icon);
        mTitle.setText(data.getTitle());
        mIcon.setBackgroundResource(data.getImageUrl());

        //Get the card background and set it
        ImageView background = getView().findViewById(R.id.wallet_card_background);
        background.setImageResource(backgroundId);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       rootView =  inflater.inflate(R.layout.fragment_wallet_card, container, false);
    
        

        return rootView;
    }

    private View rootView;
    private ItemData data;
    private int backgroundId;
}
