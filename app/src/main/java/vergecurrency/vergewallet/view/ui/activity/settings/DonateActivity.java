package vergecurrency.vergewallet.view.ui.activity.settings;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.view.ui.fragment.FragmentSend;

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

        initComponents();
    }

    private void initComponents() {
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
