package vergecurrency.vergewallet.views.activities.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.views.fragments.FragmentSend;

public class DonateActivity extends AppCompatActivity {

    Button donateButton;
    ImageView donateHeader;
    TextView donateDesc1;
    TextView donateDesc2;

    FrameLayout fl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        donateHeader = findViewById(R.id.donate_header);
        donateDesc1 = findViewById(R.id.donate_desc1);
        donateDesc2 = findViewById(R.id.donate_desc2);

        fl = findViewById(R.id.donate_content);
        fl.setVisibility(View.INVISIBLE);

        donateButton = findViewById(R.id.button_donate);
        donateButton.setOnClickListener(setOcl());
    }


    private View.OnClickListener setOcl() {
        return v -> {

            fl.setVisibility(View.VISIBLE);
            donateDesc1.setVisibility(View.INVISIBLE);
            donateDesc2.setVisibility(View.INVISIBLE);
            donateHeader.setVisibility(View.INVISIBLE);
            donateButton.setVisibility(View.INVISIBLE);

            FragmentSend fs = new FragmentSend(getText(R.string.donate_donation_address).toString());

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.donate_content, fs)
                    .commit();
        };
    }
}
