package vergecurrency.vergewallet.view.ui.activity.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import vergecurrency.vergewallet.view.base.BaseActivity;
import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.viewmodel.ShowPaperkeyViewModel;

public class ShowPaperkeyActivity extends BaseActivity {

	private ShowPaperkeyViewModel mViewModel;
	private TextView seedTextView;
	private ImageView closeButton;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		mViewModel = ViewModelProviders.of(this).get(ShowPaperkeyViewModel.class);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_paperkey_shown);
		initComponents();
	}

	//TODO : require passphrase before showing seed, create function for toggling between
	// qr and text views

	private void initComponents() {
		seedTextView = findViewById(R.id.paperkey_shown_pk);
		seedTextView.setText(mViewModel.getSeedAsText());
		closeButton = findViewById(R.id.paperkey_shown_close_button);
		closeButton.setOnClickListener(onCloseClickListener());
	}

	View.OnClickListener onCloseClickListener() {
		return v -> {
			//finish();
			onBackPressed();
		};
	}

}
