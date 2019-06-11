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
        mViewModel.nextWord(wordView, nextButton);
        throw new RuntimeException();
    }

    private void generateSeed() {
        mViewModel.generateMnemonics();
        try {
            seed = mViewModel.getSeed();
        } catch (Exception e) {
            //TODO ...
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
            if (mViewModel.isPaperKeyCompleted()) {
                startActivity(new Intent(getApplicationContext(), PaperkeyVerificationActivity.class));
            }
            mViewModel.nextWord(wordView, nextButton);
        };
    }

    Button.OnClickListener onPreviousListener() {
        return v -> {
            //get to the previous word.
            mViewModel.previousWord(wordView, nextButton);
        };
    }

}
