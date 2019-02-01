package vergecurrency.vergewallet.models.dataproc;

import android.content.Context;
import android.content.SharedPreferences;

public final class PreferencesManager {

	SharedPreferences prefs;
	Context context;

	public PreferencesManager(Context context) {
		this.context = context;
		prefs = context.getSharedPreferences("com.vergecurrency.vergewallet", context.MODE_PRIVATE);
	}

	public boolean getFirstLaunch() {
		return prefs.getBoolean("firstlaunch", true);
	}

	public void setFirstLaunch(boolean firstLaunchValue) {
		prefs.edit().putBoolean("firstlaunch", firstLaunchValue).apply();
	}


	public String getSelectedCurrency() {
		return prefs.getString("selectedCurrency", "{ \"currency\" : \"USD\" , \"name\" : \"US Dollar\" ");
	}

	public void setSelectedCurrency(String selectedCurrency) {
		prefs.edit().putString("selectedCurrency", selectedCurrency).apply();
	}

}
