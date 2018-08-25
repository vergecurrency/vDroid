package vergecurrency.vergewallet.views.activities.firstlaunch;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.freehaven.tor.control.examples.Main;

import vergecurrency.vergewallet.MainActivity;
import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.models.net.layers.TorLayerGateway;



public class FLWelcomeActivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fl_welcome);

        //THIS SHIT HAS TO BE MOVED BUT WORKS FOR NOW.
        TorLayerGateway tlg = new TorLayerGateway(getApplicationContext());

        tlg.execute();

        while(!tlg.isConnected()) {

        }
        subTextView = (TextView)findViewById(R.id.launch_verge_sub);
        subTextView.setText("Secure, anonymous and private \n Android wallet for Verge. \n Send and receive XVG");

        bottomRelative = (RelativeLayout) findViewById(R.id.launch_bottom_text);
        bottomRelative.setVisibility(View.GONE);

        //Get the Shared preferences from the app
        prefs = getSharedPreferences("com.vergecurrency.vergewallet", MODE_PRIVATE);

        if(prefs.getBoolean("firstlaunch", true)) {
            //go to first launch activity that I haven't created yet

            prefs.edit().putBoolean("firstlaunch", false).apply();
        } else {


        }
        startActivity(new Intent(this, MainActivity.class));
    }


    //Variables go here
    SharedPreferences prefs;
    TextView subTextView;
    RelativeLayout bottomRelative;

}
