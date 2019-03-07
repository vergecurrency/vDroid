package vergecurrency.vergewallet.view.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.testfairy.TestFairy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.service.model.Preferences;
import vergecurrency.vergewallet.view.ui.activity.firstlaunch.FirstLaunchActivity;

public class SplashActivity extends AppCompatActivity {

	Preferences pm;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//TestFairy token for getting info on beta testing
		TestFairy.begin(this, "a67a4df6e2a8a0c981638eb0f168297fd45aed73");

		//gets the holy preferences
		pm = new Preferences(this.getApplicationContext());

		setContentView(R.layout.activity_splash);

		if (pm.getFirstLaunch()) {
			finish();
			startActivity(new Intent(getApplicationContext(), FirstLaunchActivity.class));
		} else {
			finish();
			startActivity(new Intent(getApplicationContext(), WalletActivity.class));
		}

	}

	//For now I let it like this, I want no action on back pressed in the welcome activity.
	@Override
	public void onBackPressed() {
	}


	@Override
	protected void onResume() {
		super.onResume();

	}


}
