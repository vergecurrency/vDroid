package vergecurrency.vergewallet.views.activities.setup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.views.activities.MainActivity;

public class SetupDoneActivity extends AppCompatActivity {

    Button openWallet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_ready);

        openWallet = findViewById(R.id.button_open_wallet);
        openWallet.setOnClickListener(openWalletButtonListener());

    }

    Button.OnClickListener openWalletButtonListener() {
        return new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        };
    }
}
