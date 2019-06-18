package vergecurrency.vergewallet;

import android.app.Application;
import android.util.Log;

import io.horizontalsystems.bitcoinkit.BitcoinKit;
import vergecurrency.vergewallet.helpers.utils.LanguagesUtils;
import vergecurrency.vergewallet.service.model.PreferencesManager;

public class App extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		BitcoinKit.Companion.init(this);
		LanguagesUtils.setLocale(getApplicationContext(), PreferencesManager.init(getApplicationContext()).getCurrentLanguage());
	}
}
