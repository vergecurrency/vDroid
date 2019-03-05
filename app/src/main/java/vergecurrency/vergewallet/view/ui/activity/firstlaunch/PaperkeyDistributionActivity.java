package vergecurrency.vergewallet.view.ui.activity.firstlaunch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.service.model.Preferences;
import vergecurrency.vergewallet.wallet.WalletManager;

public class PaperkeyDistributionActivity extends AppCompatActivity {

	Button nextButton;
	Button previousButton;
	TextView wordView;
	int currentWord = -1;
	Preferences pm;

	String[] seed;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_paperkey_seed);

		pm = new Preferences(getApplicationContext());

		wordView = findViewById(R.id.paperkey_logo);

		nextButton = findViewById(R.id.paperkey_next_word);
		nextButton.setOnClickListener(onNextListener());

		previousButton = findViewById(R.id.paperkey_previous_word);
		previousButton.setOnClickListener(onPreviousListener());

		WalletManager wm = new WalletManager();
		seed = wm.generateSeed();
		pm.setMnemonic(seed);

		//get the first word
		nextWord();

	}

	Button.OnClickListener onNextListener() {
		return v -> {
			//get to the next word. For now get to the main Activity
			if (currentWord == 11) {

				startActivity(new Intent(getApplicationContext(), PaperkeyUserVerifyActivity.class));
			}
			nextWord();
		};
	}

	Button.OnClickListener onPreviousListener() {
		return v -> {
			//get to the previous word.
			previousWord();
		};
	}

	private void nextWord() {
		//Increase if not the last
		if (currentWord < 11) {
			currentWord += 1;
			nextButton.setText("Next");
		}
		//change button text if last
		if (currentWord == 11) {
			nextButton.setText("Done");
		}
		wordView.setText(getWord());
	}

	private void previousWord() {
		//Decrease if not the first
		if (currentWord > 0) {
			currentWord -= 1;
			nextButton.setText("Next");
		}
		wordView.setText(getWord());
	}

	private String getWord() {
		return seed[currentWord];
	}
}
