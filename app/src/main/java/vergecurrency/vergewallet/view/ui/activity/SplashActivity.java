package vergecurrency.vergewallet.view.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.io.IOException;
import java.security.GeneralSecurityException;

import vergecurrency.vergewallet.helpers.utils.LanguagesUtils;
import vergecurrency.vergewallet.helpers.utils.UIUtils;
import vergecurrency.vergewallet.view.base.BaseActivity;
import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.service.model.PreferencesManager;
import vergecurrency.vergewallet.view.ui.activity.firstlaunch.FirstLaunchActivity;
import vergecurrency.vergewallet.wallet.WalletManager;

public class SplashActivity extends BaseActivity {
	//DbOpenHelper dbOpenHelper;
	//private AbstractDaoSession daoSession;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//TestFairy token for getting info on beta testing
		//TestFairy.begin(this, "a67a4df6e2a8a0c981638eb0f168297fd45aed73");
		//init db
		//dbOpenHelper = new DbOpenHelper(this,"verge.db",1);
		//Database db = dbOpenHelper.getWritableDb();
		//daoSession = new AbstractDaoMaster;

		//gets the holy preferences


		try {
			PreferencesManager.initEncryptedPreferences(this);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		setContentView(R.layout.activity_splash);

		//Just to have the splash screen going briefly
		new Handler().postDelayed(this::startApplication, 2000);
	}

	private Context updateBaseContextLocale(Context context) {
		return LanguagesUtils.setLocale(context, PreferencesManager.getCurrentLanguage());
	}


	public void startApplication() {

		if (PreferencesManager.getFirstLaunch()) {
			startActivity(new Intent(getApplicationContext(), FirstLaunchActivity.class));
			finish();
		} else {
			try {
				WalletManager.startWallet();
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
			}
			startActivity(new Intent(getApplicationContext(), WalletActivity.class));
			finish();
		}
	}


	@Override
	public void onBackPressed() {
	}

}
