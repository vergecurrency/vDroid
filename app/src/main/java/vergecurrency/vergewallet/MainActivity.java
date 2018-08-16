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

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_wallet:
                    mTextMessage.setText(R.string.title_wallet);
                    mTextMessage.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    mTextMessage.setTextColor(Color.WHITE);
                    showFragment(new FragmentWallet());
                    return true;
                case R.id.navigation_transactions:
                    mTextMessage.setText(R.string.title_transactions);
                    mTextMessage.setBackgroundColor(Color.WHITE);
                    mTextMessage.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    return true;
                case R.id.navigation_send:
                    mTextMessage.setText(R.string.title_send);
                    mTextMessage.setBackgroundColor(Color.WHITE);
                    mTextMessage.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    return true;
                case R.id.navigation_receive:
                    mTextMessage.setText(R.string.title_receive);
                    mTextMessage.setBackgroundColor(Color.WHITE);
                    mTextMessage.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    return true;
                case R.id.navigation_settings:
                    mTextMessage.setText(R.string.title_settings);
                    mTextMessage.setBackgroundColor(Color.WHITE);
                    mTextMessage.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.mTextMessage);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);

    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }
}
