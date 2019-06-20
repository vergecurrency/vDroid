package vergecurrency.vergewallet.view.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.view.ui.fragment.FragmentReceive;
import vergecurrency.vergewallet.view.ui.fragment.FragmentSend;
import vergecurrency.vergewallet.view.ui.fragment.FragmentSettings;
import vergecurrency.vergewallet.view.ui.fragment.FragmentTransactions;
import vergecurrency.vergewallet.view.ui.fragment.FragmentWallet;

//TODO Define LockActivity as super class
public class WalletActivity extends AppCompatActivity /* extends LockActivity */ {

	private int currentItem;

	private TextView mTextMessage;
	//Listens to what has been pressed and opens up the right Fragment
	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
			= new BottomNavigationView.OnNavigationItemSelectedListener() {

		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem item) {
			if (item.getItemId() != currentItem) {
				currentItem = item.getItemId();
				switch (item.getItemId()) {
					case R.id.navigation_wallet:
						showFragment(new FragmentWallet(), R.string.title_wallet, Color.WHITE, getResources().getColor(R.color.verge_colorPrimary));

						return true;
					case R.id.navigation_transactions:
						showFragment(new FragmentTransactions(), R.string.title_transactions, getResources().getColor(R.color.verge_colorPrimaryDark), getResources().getColor(R.color.verge_white));

						return true;
					case R.id.navigation_send:
						showFragment(new FragmentSend(), R.string.title_send, getResources().getColor(R.color.verge_colorPrimaryDark), getResources().getColor(R.color.verge_white));

						return true;
					case R.id.navigation_receive:
						showFragment(new FragmentReceive(), R.string.title_receive, getResources().getColor(R.color.verge_colorPrimaryDark), getResources().getColor(R.color.verge_white));

						return true;
					case R.id.navigation_settings:
						showFragment(new FragmentSettings(), R.string.title_settings, getResources().getColor(R.color.verge_colorPrimaryDark), getResources().getColor(R.color.verge_white));
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
		mTextMessage = findViewById(R.id.mTextMessage);

		//Initialize Bottom bottom_navigation view
		BottomNavigationView navigation = findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
		navigation.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);

		//Shows the wallet fragment by default
		showFragment(new FragmentWallet(), R.string.title_wallet, Color.WHITE, getResources().getColor(R.color.verge_colorPrimary));
	}


	//Shows the previously selected fragment.
	private void showFragment(Fragment fragment, int title, int textColor, int textBgColor) {
		mTextMessage.setText(title);
		mTextMessage.setTextColor(textColor);
		mTextMessage.setBackgroundColor(textBgColor);

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
