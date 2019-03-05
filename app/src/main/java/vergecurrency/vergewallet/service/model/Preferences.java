package vergecurrency.vergewallet.service.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public final class Preferences {

	SharedPreferences prefs;

	public Preferences(Context context) {
		prefs = context.getSharedPreferences("com.vergecurrency.vergewallet", Context.MODE_PRIVATE);
	}

	public boolean getFirstLaunch() {
		return prefs.getBoolean("firstlaunch", true);
	}

	public void setFirstLaunch(boolean firstLaunchValue) {
		prefs.edit().putBoolean("firstlaunch", firstLaunchValue).apply();
	}


	public String getSelectedCurrency() {
		return prefs.getString("selectedCurrency", new Currency("United States Dollar", "USD").getCurrencyAsJSON());
	}

	public void setSelectedCurrency(String selectedCurrency) {
		prefs.edit().putString("selectedCurrency", selectedCurrency).apply();
	}

	public boolean getUsingTor() {
		return prefs.getBoolean("usingTor", false);
	}

	public void setUsingTor(boolean isUsingTor) {
		prefs.edit().putBoolean("usingTor", isUsingTor).apply();
	}

	public String[] getMnemonic() {

		String[] mnemonicsList =  prefs.getStringSet("mnemonic", null).toArray(new String[0]);
		return mnemonicsList;
	}

	public void setMnemonic(String[] mnemonic) {
		Set<String> mnemonicsSet = new TreeSet<>(Arrays.asList(mnemonic));

		prefs.edit().putStringSet("mnemonic", mnemonicsSet).apply();
	}

}
