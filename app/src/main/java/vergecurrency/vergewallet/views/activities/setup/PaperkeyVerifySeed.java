package vergecurrency.vergewallet.views.activities.setup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;

import vergecurrency.vergewallet.Constants;
import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.models.dataproc.SeedGenerator;
import vergecurrency.vergewallet.views.activities.MainActivity;

public class PaperkeyVerifySeed extends AppCompatActivity {

    TextView firstWordCaption;
    TextView secondWordCaption;
    EditText firstWordInput;
    EditText secondWordInput;
    Button confirmButton;
    Pair<String[], int[]> verificationWords;
    SharedPreferences prefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paperkey_confirm_seed);
        //get two random words from the seed
        verificationWords = getTwoRandomWordsFromSeed();

        //Get the shared preferences
        prefs = getSharedPreferences("com.vergecurrency.vergewallet", MODE_PRIVATE);

        initComponents();

    }

    private void initComponents() {
        //set the caption for the first word according to its position
        firstWordCaption = findViewById(R.id.label_first_word);
        firstWordCaption.setText(String.format("Word #%d", verificationWords.second[0] + 1));
        //set the caption for the second word according to its position
        secondWordCaption = findViewById(R.id.label_second_word);
        secondWordCaption.setText(String.format("Word #%d", verificationWords.second[1] + 1));

        firstWordInput = findViewById(R.id.seed_first_word);
        secondWordInput = findViewById(R.id.seed_second_word);

        //assign a click listener to the
        confirmButton = findViewById(R.id.paperkey_confirm_next);
        confirmButton.setOnClickListener(onNextClick());
    }


    private Button.OnClickListener onNextClick() {
        return new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(firstWordInput.getText().toString().equals(verificationWords.first[0])) {
                if(secondWordInput.getText().toString().equals(verificationWords.first[1])) {
                    //Announce that it's not the first launch anymore
                    prefs.edit().putBoolean("firstlaunch", false).apply();
                    //Get to the main activity
                    startActivity(new Intent(getApplicationContext(), SetupDoneActivity.class));

                }
                else {
                    secondWordInput.setBackgroundResource(R.color.material_red_200);
                }
            } else {
                firstWordInput.setBackgroundResource(R.color.material_red_200);
            }


            }
        };
    }


    private Pair<String[], int[]> getTwoRandomWordsFromSeed() {

        //get the SeedGenerator utils : name to refactor
        SeedGenerator sg = new SeedGenerator(getApplicationContext());

        //prepare the return objects
        String[] words = new String[2];
        int[] positions = new int[2];

        //get the first position user has to check
        positions[0] = sg.getRandomNumber(Constants.SEED_SIZE);
        //prepare the second one
        int second = sg.getRandomNumber(Constants.SEED_SIZE);
        //avoid having twice the same position
        while(positions[0] == second) {
            second = sg.getRandomNumber(Constants.SEED_SIZE);
        }
        //set the second position
        positions[1] = second;
        //sort the array
        Arrays.sort(positions);
        //assign the words to the positions
        words[0] = Constants.seed.get(positions[0]);
        words[1] = Constants.seed.get(positions[1]);
        //you don't need me to comment this.
        return new Pair<>(words, positions);
    }
}
