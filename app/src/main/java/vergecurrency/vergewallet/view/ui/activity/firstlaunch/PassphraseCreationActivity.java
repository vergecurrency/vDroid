package vergecurrency.vergewallet.view.ui.activity.firstlaunch;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.omega_r.libs.OmegaCenterIconButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;
import vergecurrency.vergewallet.R;

public class PassphraseCreationActivity extends AppCompatActivity {

	private ImageView eightCharsTickImageView;
	private TextView eightCharsLabel;
	private ImageView upperLowerCaseTickImageView;
	private TextView upperLowerCaseLabel;
	private ImageView specialCharsTickImageView;
	private TextView specialCharsLabel;
	private EditText passphraseEditText;
	private OmegaCenterIconButton confirmButton;

	private String passphrase;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_passphrase_create);

		initComponents();
	}


	private void initComponents() {


		eightCharsTickImageView = findViewById(R.id.passphrase_tick_8_chars);
		eightCharsLabel = findViewById(R.id.passphrase_label_8_chars);

		upperLowerCaseTickImageView = findViewById(R.id.passphrase_tick_upperlower);
		upperLowerCaseLabel = findViewById(R.id.passphrase_label_upperlower);

		specialCharsTickImageView = findViewById(R.id.passphrase_ticked_special_chars);
		specialCharsLabel = findViewById(R.id.passphrase_label_special_chars);

		confirmButton = findViewById(R.id.passphrase_confirm_button);
		confirmButton.setOnClickListener(onConfirmButtonClick());

		passphraseEditText = findViewById(R.id.passphrase_field_enter);
		passphraseEditText.addTextChangedListener(passphraseTextChangedListener());
	}

	private TextWatcher passphraseTextChangedListener() {
		return new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				validatePassphrase(s.toString());
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		};

	}

	private void validatePassphrase(String passphrase) {
		boolean isEightChars = isPassphraseEightCharsLong(passphrase);
		boolean isUpperLowerCase = hasPassphraseLowerCaseChar(passphrase) && hasPassphraseUpperCaseChar(passphrase);
		boolean isSpecialChar = isPassphraseContainingSpecialChars(passphrase);

		if (isEightChars) {
			ImageViewCompat.setImageTintList(eightCharsTickImageView, ColorStateList.valueOf(getResources().getColor(R.color.material_green_500)));
			eightCharsLabel.setTextColor(getResources().getColor(R.color.material_green_500));
		} else {
			ImageViewCompat.setImageTintList(eightCharsTickImageView, ColorStateList.valueOf(getResources().getColor(R.color.verge_colorAccent)));
			eightCharsLabel.setTextColor(getResources().getColor(R.color.verge_colorAccent));
		}

		if (isUpperLowerCase) {
			ImageViewCompat.setImageTintList(upperLowerCaseTickImageView, ColorStateList.valueOf(getResources().getColor(R.color.material_green_500)));
			upperLowerCaseLabel.setTextColor(getResources().getColor(R.color.material_green_500));
		} else {
			ImageViewCompat.setImageTintList(upperLowerCaseTickImageView, ColorStateList.valueOf(getResources().getColor(R.color.verge_colorAccent)));
			upperLowerCaseLabel.setTextColor(getResources().getColor(R.color.verge_colorAccent));
		}

		if (isSpecialChar) {
			ImageViewCompat.setImageTintList(specialCharsTickImageView, ColorStateList.valueOf(getResources().getColor(R.color.material_green_500)));
			specialCharsLabel.setTextColor(getResources().getColor(R.color.material_green_500));
		} else {
			ImageViewCompat.setImageTintList(specialCharsTickImageView, ColorStateList.valueOf(getResources().getColor(R.color.verge_colorAccent)));
			specialCharsLabel.setTextColor(getResources().getColor(R.color.verge_colorAccent));
		}
		boolean isAllOk = isEightChars && isUpperLowerCase && isSpecialChar;
		confirmButton.setEnabled(isAllOk);
		if(isAllOk){
			confirmButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.verge_colorPrimary)));
		} else {
			confirmButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.verge_colorAccent)));
		}


		this.passphrase = passphrase;
	}

	private boolean isPassphraseEightCharsLong(String s) {
		if (s.length() >= 8) {
			return true;
		} else return false;
	}

	private boolean hasPassphraseUpperCaseChar(String s) {
		if (s == s.toLowerCase()) {
			return false;
		} else return true;
	}

	private boolean hasPassphraseLowerCaseChar(String s) {
		if (s == s.toUpperCase()) {
			return false;
		} else return true;
	}

	private boolean isPassphraseContainingSpecialChars(String s) {
		if (s.matches("[A-Za-z0-9 ]*")) {
			return false;
		} else return true;
	}

	private View.OnClickListener onConfirmButtonClick() {
		return v -> {
			Intent i = new Intent(getApplicationContext(), PassphraseValidationActivity.class);
			i.putExtra("passphrase", passphrase);
			startActivity(i);
		};
	}

}
