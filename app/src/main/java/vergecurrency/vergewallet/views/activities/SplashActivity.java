package vergecurrency.vergewallet.views.activities;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.balram.locker.view.AppLocker;
import com.testfairy.TestFairy;

import org.json.JSONException;
import org.json.JSONObject;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.models.net.layers.TorLayerGateway;
import vergecurrency.vergewallet.views.activities.setup.SetupWalletActivity;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences prefs;
    ImageView vergeLogo;
	ObjectAnimator oa;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //TestFairy token for getting info on beta testing
        TestFairy.begin(this, "a67a4df6e2a8a0c981638eb0f168297fd45aed73");
        
        //gets the holy preferences
        prefs = getSharedPreferences("com.vergecurrency.vergewallet", MODE_PRIVATE);
        
        setContentView(R.layout.activity_splash);
		//Get a handler to execute stuff only after setting the content view
		final Handler handler = new Handler();
	
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
    
	@SuppressLint("StaticFieldLeak")
	private void run() {
        //Launch AppLocker
        AppLocker.getInstance().enableAppLock(getApplication());
        
		oa = instanciateAnimatedLogo();
		oa.start();
		chooseLaunchAction();
    }

    private void chooseLaunchAction() {
	
		if (prefs.getBoolean("firstlaunch", true)) {
		
			startActivity(new Intent(getApplicationContext(), SetupWalletActivity.class));
			finish();
		} else {
		
			if (!launchTor()) {
				//message
				Toast.makeText(getApplicationContext(),"TOR : Unable to connect. Please ensure the phone is connected to the internet", Toast.LENGTH_LONG).show();
				System.exit(1);
			} else {
				//Start MainActivity
				startActivity(new Intent(getApplicationContext(), MainActivity.class));
				finish();
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
			
			if (tlg.isConnected()) {
				//Get the current public IP, just for fun honestly.
				String IP = readIP(tlg.retrieveDataFromService("https://api.ipify.org?format=json"));
		
				Toast.makeText(getApplicationContext(),String.format("Tor connected. IP : %s", IP), Toast.LENGTH_LONG).show();
		
				return tlg.isConnected();
			} else {
				//Implement timeout
		
			}

        }
    }
	
	public ObjectAnimator instanciateAnimatedLogo() {
		vergeLogo = findViewById(R.id.splash_verge_logo_notext);
		
    	ObjectAnimator oa = ObjectAnimator.ofFloat(vergeLogo, "rotationY", 0f, 180f);
    	oa.setDuration(2000);
    	oa.setRepeatCount(ValueAnimator.INFINITE);
    	
		return oa;
		
		
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
