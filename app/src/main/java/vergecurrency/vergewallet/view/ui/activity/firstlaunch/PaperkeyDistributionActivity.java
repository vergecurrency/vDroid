package vergecurrency.vergewallet.view.ui.activity.firstlaunch;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.service.model.PreferencesManager;
import vergecurrency.vergewallet.viewmodel.PaperkeyDistributionViewModel;
import vergecurrency.vergewallet.wallet.WalletManager;

public class PaperkeyDistributionActivity extends AppCompatActivity {

	PaperkeyDistributionViewModel mViewModel;

	Button nextButton;
	Button previousButton;
	TextView wordView;
	int currentWord = -1;
	PreferencesManager pm;

	String[] seed;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_paperkey_seed);

		pm = PreferencesManager.getInstance();
		mViewModel = ViewModelProviders.of(this).get(PaperkeyDistributionViewModel.class);

		initComponents();

		generateSeed();
		//get the first word
		nextWord();

	}

	private void generateSeed() {
		mViewModel.generateMnemonics();
		try {
			seed = mViewModel.getSeed();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void initComponents() {
		wordView = findViewById(R.id.paperkey_logo);

		nextButton = findViewById(R.id.paperkey_next_word);
		nextButton.setOnClickListener(onNextListener());

		previousButton = findViewById(R.id.paperkey_previous_word);
		previousButton.setOnClickListener(onPreviousListener());
	}

	Button.OnClickListener onNextListener() {
		return v -> {
			//get to the next word. For now get to the main Activity
			if (currentWord == 11) {

				startActivity(new Intent(getApplicationContext(), PaperkeyVerificationActivity.class));
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
