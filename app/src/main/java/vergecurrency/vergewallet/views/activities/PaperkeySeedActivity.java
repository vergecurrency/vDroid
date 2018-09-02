package vergecurrency.vergewallet.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import vergecurrency.vergewallet.R;

public class PaperkeySeedActivity extends AppCompatActivity {

    Button nextButton;
    Button previousButton;
    TextView wordView;
    int currentWord = -1;
    String[] list = new String[]{"android", "app", "is", "coming", "and", "it", "looks", "freaking", "amazing", "thanks", "to", "you"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paperkey_seed);

        wordView = findViewById(R.id.paperkey_logo);

        nextButton = findViewById(R.id.paperkey_next_word);
        nextButton.setOnClickListener(onNextListener());

        previousButton = findViewById(R.id.paperkey_previous_word);
        previousButton.setOnClickListener(onPreviousListener());

        nextWord();

    }

    Button.OnClickListener onNextListener() {
        return new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                //get to the next word. For now get to the main Activity
                if(currentWord ==11) {
                    nextButton.setText("Done");
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
                //get to the next word. For now get to the main Activity
                previousWord();
            }
        };
    }

    private void nextWord() {
        if (currentWord < 11) {
            currentWord += 1;
        }
        wordView.setText(list[currentWord]);
    }

    private void previousWord() {
        if (currentWord > 0) {
            currentWord -= 1;
        }
        wordView.setText(list[currentWord]);
    }
}
