package vergecurrency.vergewallet.views.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.balram.locker.view.AppLocker;

import org.json.JSONException;
import org.json.JSONObject;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.models.net.layers.TorLayerGateway;
import vergecurrency.vergewallet.views.activities.setup.SetupWalletActivity;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences prefs;
    TextView torChargingView;

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
    public void onBackPressed() {
    }

    private void run() {

        //Launch AppLocker
        AppLocker.getInstance().enableAppLock(getApplication());

        boolean isFirstLaunch = prefs.getBoolean("firstlaunch", true);

        //Should be if (isFirstLaunch) but I need to get into this every time because I'm working on it.
        if (isFirstLaunch) {


            startActivity(new Intent(getApplicationContext(), SetupWalletActivity.class));
            finish();


        } else {

            if (!launchTor()) {
                //message
                Toast.makeText(this,"TOR : Unable to connect. Please ensure the phone is connected to the internet", Toast.LENGTH_LONG);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.exit(1);
            } else {
                //Start MainActivity
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

            Toast.makeText(this,String.format("Tor connected. IP : %s", IP), Toast.LENGTH_LONG);

        return tlg.isConnected();
    }

    //to be moved into a apify parser
    private String readIP(String rawData) {

        if (rawData != null && !rawData.equals("")) {
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

}
