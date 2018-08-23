package vergecurrency.vergewallet;

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

import com.msopentech.thali.toronionproxy.OnionProxyManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;

import vergecurrency.vergewallet.UI.fragments.FragmentReceive;
import vergecurrency.vergewallet.UI.fragments.FragmentSend;
import vergecurrency.vergewallet.UI.fragments.FragmentSettings;
import vergecurrency.vergewallet.UI.fragments.FragmentTransactions;
import vergecurrency.vergewallet.UI.fragments.FragmentWallet;

import vergecurrency.vergewallet.Workers.TorLayerGateway;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //THIS SHIT HAS TO BE MOVED BUT WORKS FOR NOW.
        new TorLayerGateway(getApplicationContext()).execute();


        mTextMessage = (TextView) findViewById(R.id.mTextMessage);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);

        showFragment(new FragmentWallet(), R.string.title_wallet,Color.WHITE,getResources().getColor(R.color.colorPrimary));

    }

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
    }

    private TextView mTextMessage;
}
