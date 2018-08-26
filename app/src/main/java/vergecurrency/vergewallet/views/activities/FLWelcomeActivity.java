package vergecurrency.vergewallet.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.models.net.layers.TorLayerGateway;
import vergecurrency.vergewallet.models.sec.PinCodeCheck;


public class FLWelcomeActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //gets the holy preferences
        prefs = getSharedPreferences("com.vergecurrency.vergewallet", MODE_PRIVATE);
        //Setup the pincode check
        check = PinCodeCheck.getInstance(this.getApplicationContext());

        //Get a handler to execute stuff only after setting the content view
        final Handler handler = new Handler();
        setContentView(R.layout.activity_splash);

        //TODO : Once controller has been implemented delete this, it's just to not block http client called from main threads.
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Handler that waits to view to be displayed before starting tor.
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setupWallet();
            }
        }, 500);
    }


    //For now I let it like this, I want no action on back pressed in the welcome activity.
    @Override
    public void onBackPressed() { }

    //TODO : Refactor into atomic functions
    private void setupWallet() {


        TorLayerGateway tlg = new TorLayerGateway(getApplicationContext());
        tlg.execute();
        while (!tlg.isConnected()) { }

        //Get the current public IP, just for fun honestly.
        String IP = readIP(tlg.retrieveDataFromService("https://api.ipify.org?format=json"));

        torChargingView = (TextView) findViewById(R.id.textview_launch_tor);
        torChargingView.setText("Tor connected. IP : " + IP);

        //Get the Shared preferences from the app

        boolean isFirstLaunch = prefs.getBoolean("firstlaunch", true);

        //Should be if (isFirstLaunch) but I need to get into this everytime because I'm working on it.
        if (!isFirstLaunch) {

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
            bottomButton = (Button)findViewById(R.id.button_create_wallet);
            bottomButton.setText(Html.fromHtml(getResources().getString(R.string.firstlaunch_new_wallet)), Button.BufferType.SPANNABLE);
            bottomButton.setOnClickListener( new Button.OnClickListener() {

                @Override
                public void onClick(View v) {

                    /*Get to the pin view and pin will detect it's first launch to do pin setup and
                    redirect to the rest of the creation process. Or come back here, have to see how I'm doing this.
                     */
                    startActivity(new Intent(v.getContext(), SetPinActivity.class));
                }
            });

            //create "Restore wallet"  button and its and listener. Yeah I reuse the bottomButton. I'm an eco-nazi bro.
            bottomButton = (Button)findViewById(R.id.button_restore_wallet);
            bottomButton.setText(Html.fromHtml(getResources().getString(R.string.firstlaunch_restore_wallet)), Button.BufferType.SPANNABLE);
            bottomButton.setOnClickListener( new Button.OnClickListener() {

                @Override
                public void onClick(View v) {

                    //Not definitive. Wallet restore view will come at some point
                    startActivity(new Intent(v.getContext(), SetPinActivity.class));

                }
            });

            //TODO : Following line should be used only when wallet has been created.
            // prefs.edit().putBoolean("firstlaunch", false).apply();


        } else {
            //ask for pin
            startActivity(new Intent(getApplicationContext(), SetPinActivity.class));
        }
    }


    //to be moved into a apify parser
    private String readIP(String rawData) {

        if(rawData != null || rawData != "") {
            JSONObject reader;
            try {
                reader = new JSONObject(rawData);
                return reader.getString("ip");
            } catch (JSONException e) {
                e.printStackTrace();
                return "error";
            }
        } else return "error";

    }


    @Override
    protected void onResume() {
        super.onResume();
       /* boolean isFirstLaunch = prefs.getBoolean("firstlaunch", true);
        //If it's first launch, don't trigger the pin request...
        if(!isFirstLaunch) {
            //...Otherwise do.
            if (check.isLocked()) {
                startActivity(new Intent(getApplicationContext(), SetPinActivity.class));
            }
        }*/
    }

    //Variables go here
    SharedPreferences prefs;
    TextView subTextView;
    TextView torChargingView;
    RelativeLayout bottomChargingView;
    Button bottomButton;
    private static PinCodeCheck check ;

}
