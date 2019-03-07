package vergecurrency.vergewallet.view.ui.activity.firstlaunch;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import vergecurrency.vergewallet.R;

public class PaperkeyInstructionsActivity extends AppCompatActivity {


	//The dumbest class you'll find here... jump to the next one
	Button nextButton;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_paperkey_instructions);

		nextButton = findViewById(R.id.paperkey_next_button);
		nextButton.setOnClickListener(nextButtonListener());
	}

	private Button.OnClickListener nextButtonListener() {
		return v -> {
			finish();
			startActivity(new Intent(getApplicationContext(), PaperkeyDistributionActivity.class));
		};
	}
}
