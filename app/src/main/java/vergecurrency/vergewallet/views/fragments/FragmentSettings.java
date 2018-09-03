package vergecurrency.vergewallet.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.views.fragments.beans.HeaderData;
import vergecurrency.vergewallet.views.fragments.beans.ItemData;
import vergecurrency.vergewallet.views.fragments.workers.SettingsListsAdapter;

public class FragmentSettings extends Fragment {


    public FragmentSettings() {
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
        ItemData itemsDataWallet[] = {
                new ItemData("Disconnect this device", R.drawable.icon_disconnected),
                new ItemData("Paperkey", R.drawable.icon_paperkey)
        };

        ItemData itemsDataOther[] = {
                new ItemData("Credits", R.drawable.icon_credits),
                new ItemData("Donate", R.drawable.icon_donate),
                new ItemData("Rate app", R.drawable.icon_star),
                new ItemData("Website", R.drawable.icon_web),
                new ItemData("Contribute", R.drawable.icon_github)
        };

        ItemData itemsDataSettings[] = {
                new ItemData("Fiat currency", R.drawable.icon_currency_exchange),
                new ItemData("Change wallet PIN", R.drawable.icon_pin),
                new ItemData("Use fingerprint", R.drawable.icon_fingerprint)
        };

        //populate the recyclerviews with their data
        fillRecyclerView(rootView, R.id.settings_list_wallet, new HeaderData("WALLET"), itemsDataWallet);
        fillRecyclerView(rootView,R.id.settings_list_other, new HeaderData("OTHER"),itemsDataOther );
        fillRecyclerView(rootView,R.id.settings_list_settings, new HeaderData("SETTINGS"),itemsDataSettings);

        return rootView;
    }
}
