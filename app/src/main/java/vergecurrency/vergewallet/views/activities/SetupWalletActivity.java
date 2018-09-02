package vergecurrency.vergewallet.views.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.balram.locker.utils.Locker;
import com.balram.locker.view.AppLocker;
import com.balram.locker.view.LockActivity;

import vergecurrency.vergewallet.R;

public class SetupWalletActivity extends LockActivity {

    TextView subTextView;
    RelativeLayout bottomChargingView;
    Button bottomButton;
    SharedPreferences prefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //gets the holy preferences
        prefs = getSharedPreferences("com.vergecurrency.vergewallet", MODE_PRIVATE);

        instantiateView();
    }

    private void instantiateView() {
        //Subtext to describe wallet
        subTextView = (TextView) findViewById(R.id.launch_verge_sub);
        subTextView.setText("Secure, anonymous and private \n Android wallet for Verge. \n Send and receive XVG");

        //hide "Privacy is our standard" layout
        bottomChargingView = (RelativeLayout) findViewById(R.id.launch_bottom_text);
        bottomChargingView.setVisibility(View.GONE);
        //show "Create or restore a wallet" layout
        bottomChargingView = (RelativeLayout) findViewById(R.id.launch_bottom_firstlaunch);
        bottomChargingView.setVisibility(View.VISIBLE);

        //create "Create new wallet" button and its and listener
        bottomButton = (Button) findViewById(R.id.button_create_wallet);
        bottomButton.setText(Html.fromHtml(getResources().getString(R.string.firstlaunch_new_wallet)), Button.BufferType.SPANNABLE);
        bottomButton.setOnClickListener(newWalletOnClickListener());

        //create "Restore wallet"  button and its and listener. Yeah I reuse the bottomButton. I'm an eco-nazi bro.
        bottomButton = (Button) findViewById(R.id.button_restore_wallet);
        bottomButton.setText(Html.fromHtml(getResources().getString(R.string.firstlaunch_restore_wallet)), Button.BufferType.SPANNABLE);
        bottomButton.setOnClickListener(restoreWalletOnClickListener());
    }


    private Button.OnClickListener newWalletOnClickListener() {
        return new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                int type = AppLocker.getInstance().getAppLock().isPasscodeSet() ? Locker.DISABLE_PASSLOCK : Locker.ENABLE_PASSLOCK;
                Intent intent = new Intent(getApplicationContext(), LockActivity.class);
                intent.putExtra(Locker.TYPE, type);
                startActivityForResult(intent, type);
            }
        };
    }

    private Button.OnClickListener restoreWalletOnClickListener() {
        return new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //HERE TOO YOU ASSHOLE OF A DEV
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Locker.DISABLE_PASSLOCK:
                break;
            case Locker.ENABLE_PASSLOCK:
            case Locker.CHANGE_PASSWORD:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(this, getString(R.string.setup_passcode),
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
        updateUI();
    }

    private void updateUI() {
        if (AppLocker.getInstance().getAppLock().isPasscodeSet()) {
            // go to wallet generation. but for now let's go to main activity.
            //prefs.edit().putBoolean("firstlaunch", false).apply();
            startActivity(new Intent(this, PaperkeyInstructionsActivity.class));
        }
    }
}
