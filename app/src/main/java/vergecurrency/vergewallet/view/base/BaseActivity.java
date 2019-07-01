package vergecurrency.vergewallet.view.base;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import vergecurrency.vergewallet.helpers.utils.LanguagesUtils;
import vergecurrency.vergewallet.helpers.utils.ThemeUtils;
import vergecurrency.vergewallet.service.model.PreferencesManager;

public class BaseActivity extends AppCompatActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		ThemeUtils.setTheme(PreferencesManager.getCurrentTheme(),this,false);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void attachBaseContext(Context base) {

		super.attachBaseContext(updateBaseContextLocale(base));
	}

	private Context updateBaseContextLocale(Context context) {
		return LanguagesUtils.setLocale(context, PreferencesManager.getCurrentLanguage());
	}
}
