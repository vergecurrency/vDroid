package vergecurrency.vergewallet.view.ui.activity.firstlaunch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import java.util.Arrays;

import vergecurrency.vergewallet.view.base.BaseActivity;
import vergecurrency.vergewallet.Constants;
import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.helpers.utils.MathUtils;
import vergecurrency.vergewallet.viewmodel.PaperkeyVerificationViewModel;

public class PaperkeyVerificationActivity extends BaseActivity {

	TextView firstWordCaption;
	TextView secondWordCaption;
	EditText firstWordInput;
	EditText secondWordInput;
	Button confirmButton;
	Pair<String[], int[]> verificationWords;
	String[] seed;

	PaperkeyVerificationViewModel mViewModel;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_paperkey_confirm_seed);

		mViewModel = ViewModelProviders.of(this).get(PaperkeyVerificationViewModel.class);

		//Get the shared preferences

		seed = mViewModel.getSeed();

		verificationWords = getTwoRandomWordsFromSeed();


		initComponents();

	}

	private void initComponents() {

		//TODO : Move this shit to viewmodel
		//set the caption for the first word according to its position
		firstWordCaption = findViewById(R.id.label_first_word);
		firstWordCaption.setText(String.format("Word #%d", verificationWords.second[0] + 1));
		//set the caption for the second word according to its position
		secondWordCaption = findViewById(R.id.label_second_word);
		secondWordCaption.setText(String.format("Word #%d", verificationWords.second[1] + 1));

		firstWordInput = findViewById(R.id.seed_first_word);
		secondWordInput = findViewById(R.id.seed_second_word);


		//assign a click listener to the
		confirmButton = findViewById(R.id.paper_key_confirm_next);
		confirmButton.setOnClickListener(onNextClick());
	}

	private Button.OnClickListener onNextClick() {
		return v -> {
			if (firstWordInput.getText().toString().equals(verificationWords.first[0].toLowerCase())) {
				if (secondWordInput.getText().toString().equals(verificationWords.first[1].toLowerCase())) {
					//Announce that it's not the first launch anymore
					//Get to the main activity
					startActivity(new Intent(getApplicationContext(), PassphraseCreationActivity.class));


				} else {
					secondWordInput.setBackgroundResource(R.color.material_red_200);
				}
			} else {
				firstWordInput.setBackgroundResource(R.color.material_red_200);
			}


		};
	}

	//TODO : Def move this shit to viewmodel
	private Pair<String[], int[]> getTwoRandomWordsFromSeed() {

		//prepare the return objects
		String[] words = new String[2];
		int[] positions = new int[2];

		//get the first position user has to check
		positions[0] = MathUtils.getRandomNumber(Constants.SEED_SIZE);
		//prepare the second one
		int second = MathUtils.getRandomNumber(Constants.SEED_SIZE);
		//avoid having twice the same position
		while (positions[0] == second) {
			second = MathUtils.getRandomNumber(Constants.SEED_SIZE);
		}
		//set the second position
		positions[1] = second;
		//sort the array
		Arrays.sort(positions);
		//assign the words to the positions
		words[0] = seed[positions[0]];
		words[1] = seed[positions[1]];
		//you don't need me to comment this.
		return new Pair<>(words, positions);
	}
}
