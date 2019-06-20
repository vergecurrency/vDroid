package vergecurrency.vergewallet.view.ui.activity.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import vergecurrency.vergewallet.BaseActivity;
import vergecurrency.vergewallet.R;

public class PaperkeyActivity extends BaseActivity {

	Button showPaperkeyButton;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_paperkey);

		showPaperkeyButton = findViewById(R.id.activity_paperkey_button);
		showPaperkeyButton.setOnClickListener(onShowPaperkeyListener());
	}


	View.OnClickListener onShowPaperkeyListener() {
		return v -> startActivity(new Intent(getApplicationContext(), ShowPaperkeyActivity.class));
	}
}
