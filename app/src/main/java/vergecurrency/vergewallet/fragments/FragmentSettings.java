package vergecurrency.vergewallet.fragments;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.fragments.beans.HeaderData;
import vergecurrency.vergewallet.fragments.beans.ItemData;
import vergecurrency.vergewallet.fragments.workers.SettingsListsAdapter;

public class FragmentSettings extends Fragment {


    public FragmentSettings() {
        // Required empty public constructor
    }

    public void fillRecyclerView(View rootView, int recyclerViewID, HeaderData headerData, ItemData[] itemsData) {
        //Get the RecyclerView
        RecyclerView recView = (RecyclerView)rootView.findViewById(recyclerViewID);

        recView.setLayoutManager(new LinearLayoutManager(getContext()));
        //Sets the adapter to the RecyclerView
        recView.setAdapter(new SettingsListsAdapter(headerData,itemsData));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        //Generate the data for the RecyclerView
        ItemData itemsDataWallet[] = {new ItemData("Disconnect this device", R.drawable.ic_disconnected),
                new ItemData("Paperkey", R.drawable.ic_paperkey)
        };

        ItemData itemsDataOther[] = {
                new ItemData("Credits", R.drawable.ic_icons_credits),
                new ItemData("Donate", R.drawable.ic_icons8_donate),
                new ItemData("Rate app", R.drawable.ic_star_black_24dp),
                new ItemData("Website", R.drawable.ic_web_black_24dp),
                new ItemData("Contribute", R.drawable.ic_icons8_github)
        };

        ItemData itemsDataSettings[] = {
                new ItemData("Fiat currency", R.drawable.ic_icons8_currency_exchange),
                new ItemData("Change wallet PIN", R.drawable.ic_icons8_pin),
                new ItemData("Use fingerprint", R.drawable.ic_fingerprint_black_24dp)
        };

        fillRecyclerView(rootView, R.id.settings_list_wallet, new HeaderData("WALLET"), itemsDataWallet);
        fillRecyclerView(rootView,R.id.settings_list_other, new HeaderData("OTHER"),itemsDataOther );
        fillRecyclerView(rootView,R.id.settings_list_settings, new HeaderData("SETTINGS"),itemsDataSettings);

        return rootView;
    }
}
