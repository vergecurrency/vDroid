package vergecurrency.vergewallet.models.dataproc;

import android.content.Context;
import android.content.SharedPreferences;

import vergecurrency.vergewallet.structs.Currency;

public final class PreferencesManager {

	SharedPreferences prefs;

	public PreferencesManager(Context context) {
		prefs = context.getSharedPreferences("com.vergecurrency.vergewallet", Context.MODE_PRIVATE);
	}

	public boolean getFirstLaunch() {
		return prefs.getBoolean("firstlaunch", true);
	}

	public void setFirstLaunch(boolean firstLaunchValue) {
		prefs.edit().putBoolean("firstlaunch", firstLaunchValue).apply();
	}


	public String getSelectedCurrency() {
		return prefs.getString("selectedCurrency",  new Currency("United States Dollar", "USD").getCurrencyAsJSON());
	}

	public void setSelectedCurrency(String selectedCurrency) {
		prefs.edit().putString("selectedCurrency", selectedCurrency).apply();
	}

}
