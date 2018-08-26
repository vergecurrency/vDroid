package vergecurrency.vergewallet.views.activities.firstlaunch;

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

import vergecurrency.vergewallet.MainActivity;
import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.models.net.layers.TorLayerGateway;


public class FLWelcomeActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Handler handler = new Handler();
        setContentView(R.layout.activity_fl_welcome);

        //This has to be refactored. I have to write controller classes yet to interface views with models.
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

    @Override
    public void onBackPressed()
    {

    }



    private void setupWallet() {

        //THIS SHIT HAS TO BE MOVED BUT WORKS FOR NOW.
        TorLayerGateway tlg = new TorLayerGateway(getApplicationContext());

        tlg.execute();

        while (!tlg.isConnected()) {

        }

        String IP = readIP(tlg.retrieveDataFromService("https://api.ipify.org?format=json"));

        torChargingView = (TextView) findViewById(R.id.textview_launch_tor);
        torChargingView.setText("Tor connected. IP : " + IP);

        //For tests, to check the ip
        try {
            Thread.sleep(4000);
        } catch (Exception e) {
            //couldn't sleep huh?
            e.printStackTrace();
        }


        //Get the Shared preferences from the app
        prefs = getSharedPreferences("com.vergecurrency.vergewallet", MODE_PRIVATE);
        boolean isFirstLaunch = prefs.getBoolean("firstlaunch", true);

        if (!isFirstLaunch) {
            //Charge elements for wallet initialization

            //Subtext to describe wallet
            subTextView = (TextView) findViewById(R.id.launch_verge_sub);
            subTextView.setText("Secure, anonymous and private \n Android wallet for Verge. \n Send and receive XVG");

            //hide "Privacy is our standard" layout
            bottomChargingView = (RelativeLayout) findViewById(R.id.launch_bottom_text);
            bottomChargingView.setVisibility(View.GONE);
            //show "Create or restore a wallet" layout
            bottomChargingView = (RelativeLayout) findViewById(R.id.launch_bottom_firstlaunch);
            bottomChargingView.setVisibility(View.VISIBLE);

            //show "Create new wallet" text
            bottomButton = (Button)findViewById(R.id.button_create_wallet);
            bottomButton.setText(Html.fromHtml(getResources().getString(R.string.launch_verge_new_wallet)), Button.BufferType.SPANNABLE);
            bottomButton.setOnClickListener( new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    startActivity(new Intent(v.getContext(), MainActivity.class));
                }
            });

            //show "Restore wallet" text
            bottomButton = (Button)findViewById(R.id.button_restore_wallet);
            bottomButton.setText(Html.fromHtml(getResources().getString(R.string.launch_verge_restore_wallet)), Button.BufferType.SPANNABLE);


            //there will be a click listener on the buttons that showed up to set a new wallet or restore the key.

            prefs.edit().putBoolean("firstlaunch", false).apply();


        } else {
            //ask for pin

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


    //Variables go here
    SharedPreferences prefs;
    TextView subTextView;
    TextView torChargingView;
    RelativeLayout bottomChargingView;
    RelativeLayout bottomChoiceView;
    Button bottomButton;

}
