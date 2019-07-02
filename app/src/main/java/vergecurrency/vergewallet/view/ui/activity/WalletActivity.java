package vergecurrency.vergewallet.view.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.view.base.BaseActivity;
import vergecurrency.vergewallet.view.ui.fragment.FragmentReceive;
import vergecurrency.vergewallet.view.ui.fragment.FragmentSend;
import vergecurrency.vergewallet.view.ui.fragment.FragmentSettings;
import vergecurrency.vergewallet.view.ui.fragment.FragmentTransactions;
import vergecurrency.vergewallet.view.ui.fragment.FragmentWallet;

public class WalletActivity extends BaseActivity {

	private int currentItem;

	//Listens to what has been pressed and opens up the right Fragment
	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
			= new BottomNavigationView.OnNavigationItemSelectedListener() {

		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem item) {
			if (item.getItemId() != currentItem) {
				currentItem = item.getItemId();
				switch (item.getItemId()) {
					case R.id.navigation_wallet:
						showFragment(new FragmentWallet());

						return true;
					case R.id.navigation_transactions:
						showFragment(new FragmentTransactions());

						return true;
					case R.id.navigation_send:
						showFragment(new FragmentSend());

						return true;
					case R.id.navigation_receive:
						showFragment(new FragmentReceive());

						return true;
					case R.id.navigation_settings:
						showFragment(new FragmentSettings());
						return true;
				}
				return false;
			}
			return false;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

		//Initialize upper text view


		//Initialize Bottom bottom_navigation view
		BottomNavigationView navigation = findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
		navigation.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);

		//Shows the wallet fragment by default
		showFragment(new FragmentWallet());
	}


	//Shows the previously selected fragment.
	private void showFragment(Fragment fragment) {


		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content, fragment)
				.commit();
	}

	//Do nothing on back button, as the only back action possible atm is going to the launcher...
	@Override
	public void onBackPressed() {
	}
}
