package vergecurrency.vergewallet.view.ui.activity.firstlaunch;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import vergecurrency.vergewallet.R;

public class FirstLaunchActivity extends AppCompatActivity {

	Button bottomButton;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_launch);

		instantiateButtons();
	}

	private void instantiateButtons() {

		//create "Create new wallet" button and its and listener
		bottomButton = findViewById(R.id.button_create_wallet);
		bottomButton.setText(Html.fromHtml(getResources().getString(R.string.first_launch_new_wallet_button)), Button.BufferType.SPANNABLE);
		bottomButton.setOnClickListener(newWalletOnClickListener());

		//create "Restore wallet"  button and its and listener.
		bottomButton = findViewById(R.id.button_restore_wallet);
		bottomButton.setText(Html.fromHtml(getResources().getString(R.string.first_launch_restore_wallet_button)), Button.BufferType.SPANNABLE);
		bottomButton.setOnClickListener(restoreWalletOnClickListener());
	}


	private Button.OnClickListener newWalletOnClickListener() {
		return v -> {
			finish();
			startActivity(new Intent(this, PaperkeyInstructionsActivity.class));
		};
	}

	private Button.OnClickListener restoreWalletOnClickListener() {
		return v -> {
			//HERE TOO YOU ASSHOLE OF A DEV
		};
	}



}
