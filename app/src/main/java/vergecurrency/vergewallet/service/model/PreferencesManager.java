package vergecurrency.vergewallet.service.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Warning fella : this class is a singleton object if you are wondering why I did that shitshow ;)
 */
public class PreferencesManager {

	static PreferencesManager INSTANCE = null;
	SharedPreferences prefs;


	private PreferencesManager(Context context) {
		prefs = context.getSharedPreferences("com.vergecurrency.vergewallet", Context.MODE_PRIVATE);
	}

	//--------Singleton methods
	public static PreferencesManager init(Context context) {
		if (INSTANCE != null) {
			throw new AssertionError("You already initialized an object of this type");
		}
		return INSTANCE = new PreferencesManager(context);
	}

	public static PreferencesManager getInstance() {
		if (INSTANCE == null) {
			throw new AssertionError("You haven't initialized an object of this type yet.");
		}
		return INSTANCE;
	}

	//---------First launch
	public boolean getFirstLaunch() {
		return prefs.getBoolean("firstlaunch", true);
	}

	public void setFirstLaunch(boolean firstLaunchValue) {
		prefs.edit().putBoolean("firstlaunch", firstLaunchValue).apply();
	}

	//---------Preferred currency
	public String getPreferredCurrency() {
		return prefs.getString("preferredCurrency", new Currency("United States Dollar", "USD").getCurrencyAsJSON());
	}

	public void setSelectedCurrency(String preferredCurrency) {
		prefs.edit().putString("preferredCurrency", preferredCurrency).apply();
	}

	//---------Using Tor
	public boolean getUsingTor() {
		return prefs.getBoolean("usingTor", false);
	}

	public void setUsingTor(boolean isUsingTor) {
		prefs.edit().putBoolean("usingTor", isUsingTor).apply();
	}


	public String getMnemonic() {

		return prefs.getString("mnemonic", null);
	}

	public void setMnemonic(String mnemonic) {
		prefs.edit().putString("mnemonic", mnemonic).apply();
	}



}
