package vergecurrency.vergewallet.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import vergecurrency.vergewallet.Constants;
import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.models.dataproc.SeedGenerator;

public class PaperkeySeedActivity extends AppCompatActivity {

    Button nextButton;
    Button previousButton;
    TextView wordView;
    int currentWord = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paperkey_seed);

        wordView = findViewById(R.id.paperkey_logo);

        nextButton = findViewById(R.id.paperkey_next_word);
        nextButton.setOnClickListener(onNextListener());

        previousButton = findViewById(R.id.paperkey_previous_word);
        previousButton.setOnClickListener(onPreviousListener());


        //Generate a valid seed from the SeedGenerator util
        Constants.seed = SeedGenerator.generateSeed();

        //get the first word
        nextWord();

    }

    Button.OnClickListener onNextListener() {
        return new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                //get to the next word. For now get to the main Activity
                if(currentWord ==11) {

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                nextWord();
            }
        };
    }

    Button.OnClickListener onPreviousListener() {
        return new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                //get to the previous word.
                previousWord();
            }
        };
    }

    private void nextWord() {
        //Increase if not the last
        if (currentWord < 11) {
            currentWord += 1;
        }
        //change button text if last
        if (currentWord == Constants.seed.size()) {
            nextButton.setText("Done");
        }
        wordView.setText(Constants.seed.get(currentWord));
    }

    private void previousWord() {
        //Decrease if not the first
        if (currentWord > 0) {
            currentWord -= 1;
        }
        wordView.setText(Constants.seed.get(currentWord));
    }
}
