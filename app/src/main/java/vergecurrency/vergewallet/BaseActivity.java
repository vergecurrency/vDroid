package vergecurrency.vergewallet;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import vergecurrency.vergewallet.helpers.utils.LanguagesUtils;
import vergecurrency.vergewallet.service.model.PreferencesManager;

public class BaseActivity extends AppCompatActivity {

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(updateBaseContextLocale(base));
	}

	private Context updateBaseContextLocale(Context context) {
		return LanguagesUtils.setLocale(context, PreferencesManager.getCurrentLanguage());
	}
}
