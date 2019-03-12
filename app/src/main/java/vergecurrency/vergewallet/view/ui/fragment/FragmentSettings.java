package vergecurrency.vergewallet.view.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.service.model.SettingsListViewData;
import vergecurrency.vergewallet.service.model.SettingsListViewHeader;
import vergecurrency.vergewallet.view.adapter.SettingsListsAdapter;
import vergecurrency.vergewallet.view.ui.activity.settings.DisconnectActivity;
import vergecurrency.vergewallet.view.ui.activity.settings.DonateActivity;
import vergecurrency.vergewallet.view.ui.activity.settings.PaperkeyActivity;
import vergecurrency.vergewallet.view.ui.activity.settings.SelectCurrencyActivity;
import vergecurrency.vergewallet.view.ui.activity.settings.TorSettingsActivity;

public class FragmentSettings extends Fragment {




	public FragmentSettings() {
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

		initWalletSettings(rootView);
		initGeneralSettings(rootView);
		initOtherSettings(rootView);

		return rootView;
	}

	private void initWalletSettings(View view) {
		SettingsListViewData itemsDataWallet[] = {
				new SettingsListViewData("Disconnect this device", R.drawable.icon_disconnected, v -> startActivity(new Intent(v.getContext(), DisconnectActivity.class))),
				new SettingsListViewData("Paperkey", R.drawable.icon_paperkey, v -> {
					startActivity(new Intent(v.getContext(), PaperkeyActivity.class));
				}),
		};

		fillRecyclerView(view, R.id.settings_list_wallet, new SettingsListViewHeader("WALLET"), itemsDataWallet);

	}


	private void initGeneralSettings(View view) {
		SettingsListViewData itemsDataSettings[] = {
				new SettingsListViewData("Fiat currency", R.drawable.icon_currency_exchange, v -> startActivity(new Intent(v.getContext(), SelectCurrencyActivity.class))),
				new SettingsListViewData("Change wallet PIN", R.drawable.icon_pin, null),
				new SettingsListViewData("Use fingerprint", R.drawable.icon_fingerprint, null),
				new SettingsListViewData("Tor connection", R.drawable.icon_onion, v -> startActivity(new Intent(v.getContext(), TorSettingsActivity.class)))
		};

		fillRecyclerView(view, R.id.settings_list_settings, new SettingsListViewHeader("SETTINGS"), itemsDataSettings);

	}

	private void initOtherSettings(View view) {
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

		fillRecyclerView(view, R.id.settings_list_other, new SettingsListViewHeader("OTHER"), itemsDataOther);
	}

	public void fillRecyclerView(View rootView, int recyclerViewID, SettingsListViewHeader settingsListViewHeader, SettingsListViewData[] itemsData) {
		//Get the RecyclerView
		RecyclerView recView = rootView.findViewById(recyclerViewID);

		recView.setLayoutManager(new LinearLayoutManager(getContext()));
		//Sets the adapter to the RecyclerView
		recView.setAdapter(new SettingsListsAdapter(settingsListViewHeader, itemsData));
		recView.addItemDecoration(new DividerItemDecoration(recView.getContext(), DividerItemDecoration.VERTICAL));

	}

}

