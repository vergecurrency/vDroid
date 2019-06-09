package vergecurrency.vergewallet.view.ui.activity.firstlaunch;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.viewmodel.PaperkeyDistributionViewModel;

public class PaperkeyDistributionActivity extends AppCompatActivity {


	//variable decl.
	PaperkeyDistributionViewModel mViewModel;

	Button nextButton;
	Button previousButton;
	TextView wordView;
	int currentWordIndex = -1;

	String[] seed;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_paperkey_seed);

		//learn it and shut up
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
			//TODO : do it better
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
			if (currentWordIndex == 11) {
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
		if (currentWordIndex < 11) {
			currentWordIndex += 1;
			nextButton.setText("Next");
		}
		//change button text if last
		if (currentWordIndex == 11) {
			nextButton.setText("Done");
		}
		wordView.setText(getWord());
	}

	//TODO : move to the viewmodel
	private void previousWord() {
		//Decrease if not the first
		if (currentWordIndex > 0) {
			currentWordIndex -= 1;
			nextButton.setText("Next");
		}
		wordView.setText(getWord());
	}

	//TODO : move to the viewmodel
	private String getWord() {
		return seed[currentWordIndex];
	}
}
