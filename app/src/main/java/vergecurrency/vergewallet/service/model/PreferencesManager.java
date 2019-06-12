package vergecurrency.vergewallet.service.model;

import android.content.Context;
import android.content.SharedPreferences;

import vergecurrency.vergewallet.Constants;

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

	//-------Pin
	public String getPin() {
		return prefs.getString("pin", "");
	}

	public void setPin(String pin) {
		prefs.edit().putString("pin", pin).apply();
	}

	//-------Pin digits
	public int getPinCount() {
		return prefs.getInt("pinCount", 6);
	}

	public void setPinCount(int pinCount) {
		prefs.edit().putInt("pinCount", pinCount).apply();
		;
	}

	//---------Preferred currency
	public String getPreferredCurrency() {
		return prefs.getString("preferredCurrency", new Currency("United States Dollar", "USD").getCurrencyAsJSON());
	}

	public void setSelectedCurrency(String preferredCurrency) {
		prefs.edit().putString("preferredCurrency", preferredCurrency).apply();
	}

	//---------Preferred language

	public String getCurrentLanguage() {
		return prefs.getString("language", new Language("English", "en").getLanguageAsJson());
	}

	public void setCurrentLanguage(String language) {
		prefs.edit().putString("language", language).apply();
	}

	//---------Using Tor
	public boolean getUsingTor() {
		return prefs.getBoolean("usingTor", false);
	}

	public void setUsingTor(boolean isUsingTor) {
		prefs.edit().putBoolean("usingTor", isUsingTor).apply();
	}

	//--------12words mnemonic
	public String getMnemonic() {
		return prefs.getString("mnemonic", null);
	}

	public void setMnemonic(String mnemonic) {
		prefs.edit().putString("mnemonic", mnemonic).apply();
	}

	//-------User passphrase
	public String getPassphrase() {
		return prefs.getString("passphrase", "mnemonic");
	}

	public void setPassphrase(String passphrase) {
		prefs.edit().putString("passphrase", passphrase).apply();
	}

	//---------Wallet amount

	public float getAmount() {
		return prefs.getFloat("amount", 0f);
	}

	public void setAmount(float amount) {
		if (amount < 0f) {
			amount = 0f;
		}
		prefs.edit().putFloat("amount", amount).apply();
	}

	//--------auth for unlocking wallet
	public boolean getLocalAuthForWalletUnlock() {
		return prefs.getBoolean("authUnlockWallet", false);
	}

	public void setLocalAuthForWalletUnlock(boolean value) {
		prefs.edit().putBoolean("authUnlockWallet", value).apply();
	}

	//--------auth for sending xvg
	public boolean getLocalAuthForSendingXVG() {
		return prefs.getBoolean("authSendXVG", false);
	}

	public void setLocalAuthForSendingXVG(boolean value) {
		prefs.edit().putBoolean("authSendXVG", value).apply();
	}

	//--------vws endpoint
	public String getWalletServiceUrl() {
		return prefs.getString("walletServiceURL", Constants.VWS_ENDPOINT);
	}

	public void setWalletServiceUrl(String url) {
		prefs.edit().putString("walletServiceURL", url).apply();
	}

	//--------walletid
	public String getWalletId() {
		return prefs.getString("walletId", null);
	}

	public void setWalletId(String id) {
		if (id == null) {
			id = "";
		}
		prefs.edit().putString("walletId", id).apply();
	}

	//--------walletname
	public String getWalletName() {
		return prefs.getString("walletName", null);
	}

	public void setWalletName(String name) {
		if (name == null) {
			name = "";
		}
		prefs.edit().putString("walletName", name).apply();
	}

	//--------walletsecret
	public String getWalletSecret() {
		return prefs.getString("walletSecret", null);
	}

	public void setWalletSecret(String secret) {
		if (secret == null) {
			secret = "";
		}
		prefs.edit().putString("walletSecret", secret).apply();
	}

	//--------walletsecret
	public String getDeviceToken() {
		return prefs.getString("deviceToken", "");
	}

	public void setDeviceToken(String token) {
		prefs.edit().putString("deviceToken", token).apply();
	}


}
