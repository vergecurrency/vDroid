package vergecurrency.vergewallet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import vergecurrency.vergewallet.models.sec.PinCodeCheck;
import vergecurrency.vergewallet.views.activities.PinlockActivity;
import vergecurrency.vergewallet.views.fragments.FragmentReceive;
import vergecurrency.vergewallet.views.fragments.FragmentSend;
import vergecurrency.vergewallet.views.fragments.FragmentSettings;
import vergecurrency.vergewallet.views.fragments.FragmentTransactions;
import vergecurrency.vergewallet.views.fragments.FragmentWallet;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Create the check for pin code
        check   = PinCodeCheck.getInstance(getApplicationContext());

        //Initialize upper text view
        mTextMessage = (TextView) findViewById(R.id.mTextMessage);

        //Initialize Bottom bottom_navigation view
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);

        //Shows the wallet fragment by default
        showFragment(new FragmentWallet(), R.string.title_wallet, Color.WHITE, getResources().getColor(R.color.colorPrimary));
    }

    //Do nothing on back button, as the only back action possible atm is going to the launcher...
    @Override
    public void onBackPressed() {
    }

    //Listens to what has been pressed and opens up the right Fragment
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_wallet:
                    showFragment(new FragmentWallet(), R.string.title_wallet,Color.WHITE,getResources().getColor(R.color.colorPrimary));

                    return true;
                case R.id.navigation_transactions:
                    showFragment(new FragmentTransactions(), R.string.title_transactions,getResources().getColor(R.color.colorPrimaryDark),Color.WHITE);

                    return true;
                case R.id.navigation_send:
                    showFragment(new FragmentSend(), R.string.title_send,getResources().getColor(R.color.colorPrimaryDark),Color.WHITE);

                    return true;
                case R.id.navigation_receive:
                    showFragment(new FragmentReceive(), R.string.title_receive,getResources().getColor(R.color.colorPrimaryDark),Color.WHITE);

                    return true;
                case R.id.navigation_settings:
                    showFragment(new FragmentSettings(), R.string.title_settings,getResources().getColor(R.color.colorPrimaryDark),Color.WHITE);
                    return true;
            }
            return false;
        }
    };

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

    @Override
    protected void onResume() {
        super.onResume();
        if (check.isLocked()) {
           // startActivity(new Intent(getApplicationContext(), PinlockActivity.class));
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        check.init();
    }

    private TextView mTextMessage;
    private static PinCodeCheck check ;
}
