package vergecurrency.vergewallet.view.ui.activity.firstlaunch;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.view.ui.activity.WalletActivity;

public class EndSetupActivity extends AppCompatActivity {

    Button openWallet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_ready);

        openWallet = findViewById(R.id.button_open_wallet);
        openWallet.setOnClickListener(openWalletButtonListener());

    }

    Button.OnClickListener openWalletButtonListener() {
        finish();
        return v -> startActivity(new Intent(getApplicationContext(), WalletActivity.class));
    }
}
