package vergecurrency.vergewallet.views.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


import vergecurrency.vergewallet.R;

public class SetupWalletActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        switchViewLayout();

        //create "Create new wallet" button and its and listener
        bottomButton = (Button)findViewById(R.id.button_create_wallet);
        bottomButton.setText(Html.fromHtml(getResources().getString(R.string.firstlaunch_new_wallet)), Button.BufferType.SPANNABLE);
        bottomButton.setOnClickListener( newWalletOnClickListener());

        //create "Restore wallet"  button and its and listener. Yeah I reuse the bottomButton. I'm an eco-nazi bro.
        bottomButton = (Button)findViewById(R.id.button_restore_wallet);
        bottomButton.setText(Html.fromHtml(getResources().getString(R.string.firstlaunch_restore_wallet)), Button.BufferType.SPANNABLE);
        bottomButton.setOnClickListener(restoreWalletOnClickListener() );


        // prefs.edit().putBoolean("firstlaunch", false).apply();
    }

    private void switchViewLayout() {
        //Subtext to describe wallet
        subTextView = (TextView) findViewById(R.id.launch_verge_sub);
        subTextView.setText("Secure, anonymous and private \n Android wallet for Verge. \n Send and receive XVG");

        //hide "Privacy is our standard" layout
        bottomChargingView = (RelativeLayout) findViewById(R.id.launch_bottom_text);
        bottomChargingView.setVisibility(View.GONE);
        //show "Create or restore a wallet" layout
        bottomChargingView = (RelativeLayout) findViewById(R.id.launch_bottom_firstlaunch);
        bottomChargingView.setVisibility(View.VISIBLE);
    }

    private Button.OnClickListener newWalletOnClickListener() {
        return new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }

    private Button.OnClickListener restoreWalletOnClickListener() {
        return new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }


    TextView subTextView;
    RelativeLayout bottomChargingView;
    Button bottomButton;
    private static final int REQUEST_CODE_ENABLE = 11;
}
