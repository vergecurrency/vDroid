package vergecurrency.vergewallet.views.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.widget.TextView;

import vergecurrency.vergewallet.Constants;
import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.models.dataproc.SeedGenerator;

public class PaperkeyVerifySeed extends AppCompatActivity {

    TextView firstWordCaption;
    TextView secondWordCaption;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paperkey_confirm_seed);

        firstWordCaption = findViewById(R.id.seed_confirm_first);
        secondWordCaption = findViewById(R.id.seed_confirm_second);


    }

    private Pair<String[],int[]> getTwoRandomWordsFromSeed() {

        String[] words = new String[2];
        int[] positions = new int[2];

        positions[0] = SeedGenerator.getRandomNumber(Constants.SEED_SIZE);
        words[0] = Constants.seed.get(positions[0]);
        positions[1] = SeedGenerator.getRandomNumber(Constants.SEED_SIZE);
        words[1] = Constants.seed.get(positions[0]);

        return new Pair<>(words,positions);

    }
}
