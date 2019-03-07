package vergecurrency.vergewallet.view.ui.activity.firstlaunch;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.service.model.Preferences;
import vergecurrency.vergewallet.viewmodel.PaperkeyDistributionViewModel;
import vergecurrency.vergewallet.wallet.WalletManager;

public class PaperkeyDistributionActivity extends AppCompatActivity {

	//that's a nasty way to get a viewmodel
	PaperkeyDistributionViewModel mViewModel = ViewModelProviders.of(this).get(PaperkeyDistributionViewModel.class);

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


		initComponents();
		//TODO : move to the viewmodel 
		generateMnemonics();

		//get the first word
		nextWord();

	}

	private void initComponents() {
		wordView = findViewById(R.id.paperkey_logo);

		nextButton = findViewById(R.id.paperkey_next_word);
		nextButton.setOnClickListener(onNextListener());

		previousButton = findViewById(R.id.paperkey_previous_word);
		previousButton.setOnClickListener(onPreviousListener());
	}

	private void generateMnemonics() {
		WalletManager wm = new WalletManager();
		seed = wm.generateSeed();
		pm.setMnemonic(seed);
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

	//TODO : move to the viewmodel
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

	//TODO : move to the viewmodel
	private void previousWord() {
		//Decrease if not the first
		if (currentWord > 0) {
			currentWord -= 1;
			nextButton.setText("Next");
		}
		wordView.setText(getWord());
	}

	//TODO : move to the viewmodel
	private String getWord() {
		return seed[currentWord];
	}
}
