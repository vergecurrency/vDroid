package vergecurrency.vergewallet.views.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.views.activities.settings.DisconnectActivity;
import vergecurrency.vergewallet.views.activities.settings.DonateActivity;
import vergecurrency.vergewallet.views.activities.settings.PaperkeyActivity;
import vergecurrency.vergewallet.views.activities.settings.SelectCurrencyActivity;
import vergecurrency.vergewallet.views.activities.settings.TorSettingsActivity;
import vergecurrency.vergewallet.structs.SettingsListViewData;
import vergecurrency.vergewallet.structs.SettingsListViewHeader;
import vergecurrency.vergewallet.models.adapters.SettingsListsAdapter;

public class FragmentSettings extends Fragment {


	public FragmentSettings() {
	}

	public void fillRecyclerView(View rootView, int recyclerViewID, SettingsListViewHeader settingsListViewHeader, SettingsListViewData[] itemsData) {
		//Get the RecyclerView
		RecyclerView recView = (RecyclerView) rootView.findViewById(recyclerViewID);

		recView.setLayoutManager(new LinearLayoutManager(getContext()));
		//Sets the adapter to the RecyclerView
		recView.setAdapter(new SettingsListsAdapter(settingsListViewHeader, itemsData));
		recView.addItemDecoration(new DividerItemDecoration(recView.getContext(), DividerItemDecoration.VERTICAL));


	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

		//Generate the data for the RecyclerView
		SettingsListViewData itemsDataWallet[] = {
				new SettingsListViewData("Disconnect this device", R.drawable.icon_disconnected, v -> startActivity(new Intent(v.getContext(), DisconnectActivity.class))),
				new SettingsListViewData("Paperkey", R.drawable.icon_paperkey, v -> {
					startActivity(new Intent(v.getContext(), PaperkeyActivity.class));
				}),
		};
		SettingsListViewData itemsDataSettings[] = {
				new SettingsListViewData("Fiat currency", R.drawable.icon_currency_exchange, v -> startActivity(new Intent(v.getContext(), SelectCurrencyActivity.class))),
				new SettingsListViewData("Change wallet PIN", R.drawable.icon_pin, null),
				new SettingsListViewData("Use fingerprint", R.drawable.icon_fingerprint, null),
				new SettingsListViewData("Tor connection", R.drawable.icon_onion, v -> startActivity(new Intent(v.getContext(), TorSettingsActivity.class)))
		};

		SettingsListViewData itemsDataOther[] = {
				new SettingsListViewData("Credits", R.drawable.icon_credits, null),
				new SettingsListViewData("Donate", R.drawable.icon_donate, v -> startActivity(new Intent(v.getContext(), DonateActivity.class))),
				new SettingsListViewData("Rate app", R.drawable.icon_star, null),
				new SettingsListViewData("Website", R.drawable.icon_web, v -> {
					Intent intent = new Intent(Intent.ACTION_VIEW,
							Uri.parse("https://www.vergecurrency.com"));
					startActivity(intent);
				}),
				new SettingsListViewData("Contribute", R.drawable.icon_github, v -> {
					Intent intent = new Intent(Intent.ACTION_VIEW,
							Uri.parse("https://www.github.com/vergecurrency/vDroid"));
					startActivity(intent);
				})
		};


		//populate the recyclerviews with their data
		fillRecyclerView(rootView, R.id.settings_list_wallet, new SettingsListViewHeader("WALLET"), itemsDataWallet);
		fillRecyclerView(rootView, R.id.settings_list_settings, new SettingsListViewHeader("SETTINGS"), itemsDataSettings);
		fillRecyclerView(rootView, R.id.settings_list_other, new SettingsListViewHeader("OTHER"), itemsDataOther);

		return rootView;
	}
}
