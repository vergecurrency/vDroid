package vergecurrency.vergewallet.view.ui.activity.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import vergecurrency.vergewallet.view.base.BaseActivity;
import vergecurrency.vergewallet.R;

public class PaperkeyActivity extends BaseActivity {

	Button showPaperKeyButton;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_paperkey);

		showPaperKeyButton = findViewById(R.id.activity_paperkey_button);
		showPaperKeyButton.setOnClickListener(onShowPaperKeyListener());
	}


	View.OnClickListener onShowPaperKeyListener() {

		return v -> {
			finish();
			startActivity(new Intent(getApplicationContext(), ShowPaperkeyActivity.class));
		};
	}
}
