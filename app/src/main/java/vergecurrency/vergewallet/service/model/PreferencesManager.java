package vergecurrency.vergewallet.service.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;

import vergecurrency.vergewallet.Constants;


/**
 * Warning fella : this class is a singleton object if you are wondering why I did that shitshow ;)
 */
public class PreferencesManager {
    private static final String FIRST_LAUNCH = "firstlaunch";
    private static final String PIN = "pin";
    private static final String PIN_COUNT = "pinCount";
    private static final String PREFERRED_CURRENCY = "preferredCurrency";
    private static final String USING_TOR = "usingTor";
    private static final String LANGUAGE = "language";
    private static final String MNEMONIC = "mnemonic";
    private static final String PASSPHRASE = "passphrase";
    private static final String AMOUNT = "amount";
    private static final String AUTH_UNLOCK_WALLET = "authUnlockWallet";
    private static final String AUTH_SEND_XVG = "authSendXVG";
    private static final String WALLET_SERVICE_URL = "walletServiceURL";
    private static final String WALLET_ID = "walletId";
    private static final String WALLET_NAME = "walletName";
    private static final String WALLET_SECRET = "walletSecret";
    private static final String DEVICE_TOKEN = "deviceToken";
    private static final String PREFERRED_THEME = "preferredTheme";


    private static PreferencesManager INSTANCE = null;
    private static SharedPreferences preferences;
    private static SharedPreferences encryptedPreferences;

    private PreferencesManager(Context context) {
        preferences = context.getSharedPreferences("com.vergecurrency.vergewallet", Context.MODE_PRIVATE);
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

    public static void initEncryptedPreferences(Context context) throws GeneralSecurityException, IOException {
        if (encryptedPreferences == null) {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            encryptedPreferences = EncryptedSharedPreferences.create(
                    "com.vergecurrency.vergewallet.secrets",
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        }
    }

    //-------Pin
    public static String getPin() {
        return encryptedPreferences.getString(PIN, "");
    }

    public static void setPin(String pin) {
        encryptedPreferences.edit().putString(PIN, pin).apply();
    }

    //-------Pin digits
    public static int getPinCount() {
        return encryptedPreferences.getInt(PIN_COUNT, 6);
    }

    public static void setPinCount(int pinCount) {
        encryptedPreferences.edit().putInt(PIN_COUNT, pinCount).apply();
    }

    //--------12words mnemonic
    public static String getMnemonic() {
        return encryptedPreferences.getString(MNEMONIC, null);
    }

    public static void setMnemonic(String mnemonic) {
        encryptedPreferences.edit().putString(MNEMONIC, mnemonic).apply();
    }

    //-------User passphrase
    public static String getPassphrase() {
        return preferences.getString(PASSPHRASE, "mnemonic");
    }

    public static void setPassphrase(String passphrase) {
        encryptedPreferences.edit().putString(PASSPHRASE, passphrase).apply();
    }

    //---------Preferred currency
    public static String getPreferredCurrency() {
        return encryptedPreferences.getString(PREFERRED_CURRENCY, new Currency("United States Dollar", "USD").getCurrencyAsJSON());
    }

    public static void setSelectedCurrency(String preferredCurrency) {
        encryptedPreferences.edit().putString(PREFERRED_CURRENCY, preferredCurrency).apply();
    }

    //---------Using Tor
    public static boolean getUsingTor() {
        return encryptedPreferences.getBoolean(USING_TOR, false);
    }

    public static void setUsingTor(boolean isUsingTor) {
        encryptedPreferences.edit().putBoolean(USING_TOR, isUsingTor).apply();
    }

    //---------Wallet amount

    public static float getAmount() {
        return encryptedPreferences.getFloat(AMOUNT, 0f);
    }

    public static void setAmount(float amount) {
        if (amount < 0f) {
            amount = 0f;
        }
        encryptedPreferences.edit().putFloat(AMOUNT, amount).apply();
    }

    //--------walletsecret
    public static String getWalletSecret() {
        return encryptedPreferences.getString(WALLET_SECRET, null);
    }

    public static void setWalletSecret(String secret) {
        if (secret == null) {
            secret = "";
        }
        encryptedPreferences.edit().putString(WALLET_SECRET, secret).apply();
    }

    //--------walletsecret
    public static String getDeviceToken() {
        return encryptedPreferences.getString(DEVICE_TOKEN, "");
    }

    public static void setDeviceToken(String token) {
        encryptedPreferences.edit().putString(DEVICE_TOKEN, token).apply();
    }

    //--------auth for unlocking wallet
    public static boolean getLocalAuthForWalletUnlock() {
        return encryptedPreferences.getBoolean(AUTH_UNLOCK_WALLET, false);
    }

    public static void setLocalAuthForWalletUnlock(boolean value) {
        encryptedPreferences.edit().putBoolean(AUTH_UNLOCK_WALLET, value).apply();
    }

    //--------auth for sending xvg
    public static boolean getLocalAuthForSendingXVG() {
        return encryptedPreferences.getBoolean(AUTH_SEND_XVG, false);
    }

    public static void setLocalAuthForSendingXVG(boolean value) {
        encryptedPreferences.edit().putBoolean(AUTH_SEND_XVG, value).apply();
    }

    //--------walletid
    public static String getWalletId() {
        return encryptedPreferences.getString(WALLET_ID, null);
    }

    public static void setWalletId(String id) {
        if (id == null) {
            id = "";
        }
        encryptedPreferences.edit().putString(WALLET_ID, id).apply();
    }

    //--------walletname
    public static String getWalletName() {
        return encryptedPreferences.getString(WALLET_NAME, null);
    }

    public static void setWalletName(String name) {
        if (name == null) {
            name = "";
        }
        encryptedPreferences.edit().putString(WALLET_NAME, name).apply();
    }

    /*

    non sensitive

     */

    //--------vws endpoint
    public static String getWalletServiceUrl() {
        return preferences.getString(WALLET_SERVICE_URL, Constants.VWS_ENDPOINT);
    }

    public static void setWalletServiceUrl(String url) {
        preferences.edit().putString(WALLET_SERVICE_URL, url).apply();
    }

    public static String getCurrentTheme() {
        return preferences.getString(PREFERRED_THEME, "Feather Theme");
    }

    public static void setCurrentTheme(String theme) {
        preferences.edit().putString(PREFERRED_THEME, theme).apply();
    }

    //---------Preferred language

    public static String getCurrentLanguage() {
        return preferences.getString(LANGUAGE, new Language("English", "en").getLanguageAsJson());
    }

    public static void setCurrentLanguage(String language) {
        preferences.edit().putString(LANGUAGE, language).apply();
    }

    //---------First launch
    public static boolean getFirstLaunch() {
        return preferences.getBoolean(FIRST_LAUNCH, true);
    }

    public static void setFirstLaunch(boolean firstLaunchValue) {
        preferences.edit().putBoolean(FIRST_LAUNCH, firstLaunchValue).apply();
    }

}
