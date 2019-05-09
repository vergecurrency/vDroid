package vergecurrency.vergewallet.view.ui.activity.firstlaunch;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.omega_r.libs.OmegaCenterIconButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.viewmodel.PassphraseVerificationViewModel;


public class PassphraseValidationActivity extends AppCompatActivity {

	private PassphraseVerificationViewModel mViewModel;
	private String passphraseToValidate;
	private EditText passphraseEditText;
	private OmegaCenterIconButton validateButton;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_passphrase_validate);

		passphraseToValidate = getIntent().getStringExtra("passphrase");

		mViewModel = ViewModelProviders.of(this).get(PassphraseVerificationViewModel.class);

		initComponents();
	}


	private void initComponents() {
		passphraseEditText = findViewById(R.id.passphrase_field_validate);
		passphraseEditText.addTextChangedListener(passphraseTextWatcher());
		validateButton = findViewById(R.id.button_passphrase_validate);
		validateButton.setOnClickListener(onValidateClick());

	}

	private TextWatcher passphraseTextWatcher() {
		return new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				boolean isAllOk = s.toString().equals(passphraseToValidate);
				validateButton.setEnabled(isAllOk);
				if(isAllOk){
					validateButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.verge_colorPrimary)));
				} else {
					validateButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.verge_colorAccent)));
				}
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		};
	}

	View.OnClickListener onValidateClick() {
		return v->{
			mViewModel.setFirstLaunch(false);
			mViewModel.setPassphrase(passphraseToValidate);
			finish();
			startActivity(new Intent(getApplicationContext(), EndSetupActivity.class));
		};
	}
}
