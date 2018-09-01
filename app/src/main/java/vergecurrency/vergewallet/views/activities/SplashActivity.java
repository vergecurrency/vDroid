package vergecurrency.vergewallet.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.widget.TextView;



import net.freehaven.tor.control.examples.Main;

import org.json.JSONException;
import org.json.JSONObject;

import vergecurrency.vergewallet.MainActivity;
import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.models.net.layers.TorLayerGateway;
import vergecurrency.vergewallet.models.sec.PinCodeCheck;


public class SplashActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //gets the holy preferences
        prefs = getSharedPreferences("com.vergecurrency.vergewallet", MODE_PRIVATE);

        //Get a handler to execute stuff only after setting the content view
        final Handler handler = new Handler();
        setContentView(R.layout.activity_splash);


        //Handler that waits to view to be displayed before starting tor.
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SplashActivity.this.run();
            }
        }, 500);
    }


    //For now I let it like this, I want no action on back pressed in the welcome activity.
    @Override
    public void onBackPressed() { }

    //TODO : Refactor into atomic functions
    private void run() {




        boolean isFirstLaunch = prefs.getBoolean("firstlaunch", true);

        //Should be if (isFirstLaunch) but I need to get into this every time because I'm working on it.
        if (isFirstLaunch) {


            startActivity(new Intent(getApplicationContext(), SetupWalletActivity.class));
            finish();


        } else {
            //Note : moved here because network should not be required to create a wallet.
            //TODO : if false, don't continue. -> launchTor will return a boolean


            if(!launchTor()) {
                //message
                System.exit(0); //It's there just to put something.
            } else {

                //ask for pin


                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }

        }
    }

    /**
     * Launch tor.
     */
    private boolean launchTor() {

        //TODO : Once controller has been implemented delete this, it's just to not block http client called from main threads.
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        TorLayerGateway tlg = new TorLayerGateway(getApplicationContext());
        //TODO : Check whether there's internet connection first.
        tlg.execute();

        while (true) {
            if (tlg.isConnected()) break;
            //Implement timeout
        }

        //Get the current public IP, just for fun honestly.
        String IP = readIP(tlg.retrieveDataFromService("https://api.ipify.org?format=json"));

        torChargingView = (TextView) findViewById(R.id.textview_launch_tor);
        torChargingView.setText(String.format("Tor connected. IP : %s", IP));

        return tlg.isConnected();
    }

    //to be moved into a apify parser
    private String readIP(String rawData) {

        if(rawData != null && !rawData.equals("")) {
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

    }

    //Variables go here
    SharedPreferences prefs;
    TextView torChargingView;

}
