package vergecurrency.vergewallet.view.ui.activity.firstlaunch;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import com.davidmiguel.numberkeyboard.NumberKeyboard;
import com.davidmiguel.numberkeyboard.NumberKeyboardListener;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.helpers.utils.UIUtils;
import vergecurrency.vergewallet.view.base.BaseActivity;
import vergecurrency.vergewallet.viewmodel.PinVerificationViewModel;

public class PinConfirmActivity extends BaseActivity {

	private String pinToValidate;
	private String pin;
	private String origin;
	private ImageView[] pinIVs;
	private PinVerificationViewModel mViewModel;
	private Button buttonContinue;
	private RelativeLayout confirmLayout;
	private NumberKeyboard numberKeyboard;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		pinToValidate = getIntent().getStringExtra("pin");
		mViewModel = ViewModelProviders.of(this).get(PinVerificationViewModel.class);

		setContentView(R.layout.activity_pin_confirm);
		initComponents();

		origin = getIntent().getStringExtra("origin");
	}

	private void initComponents() {
		pin = "";

		pinIVs = new ImageView[6];
		//As a second priority, make this dynamic to allow for pin size change
		pinIVs[0] = findViewById(R.id.pin_one_conf);
		pinIVs[1] = findViewById(R.id.pin_two_conf);
		pinIVs[2] = findViewById(R.id.pin_three_conf);
		pinIVs[3] = findViewById(R.id.pin_four_conf);
		pinIVs[4] = findViewById(R.id.pin_five_conf);
		pinIVs[5] = findViewById(R.id.pin_six_conf);

		numberKeyboard = findViewById(R.id.pin_number_pad);

		numberKeyboard.setLeftAuxButtonIcon(R.drawable.icon_fingerprint);
		numberKeyboard.setRightAuxButtonIcon(R.drawable.icon_backspace);

		numberKeyboard.setListener(nkl());

		buttonContinue = findViewById(R.id.pin_confirmed_button);
		buttonContinue.setOnClickListener(confirmButtonListener());

		confirmLayout = findViewById(R.id.pin_confirmed_layout);

	}

	private NumberKeyboardListener nkl() {
		return new NumberKeyboardListener() {
			@Override
			public void onNumberClicked(int i) {
				pin = pin.concat(i + "");
				changePinColors(pin.length());

				if (pin.length() == 6 && pin.equals(pinToValidate)) {
					numberKeyboard.setVisibility(View.GONE);
					confirmLayout.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onLeftAuxButtonClicked() {
				Toast.makeText(getApplicationContext(), "Fingerprint not available yet.", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onRightAuxButtonClicked() {
				if (pin.length() > 0) {
					pin = pin.substring(0, pin.length() - 1);
					changePinColors(pin.length());
				}
			}
		};
	}

	private View.OnClickListener confirmButtonListener() {
		return view -> {
			mViewModel.setPin(pin);
			if(origin.equals("firstLaunch")) {
				startActivity(new Intent(getApplicationContext(), PaperkeyInstructionsActivity.class));
			} else if (origin.equals("settings")) {
				finish();
			}
		};
	}

	private void changePinColors(int numbers) {
		for (int i = 0; i < pinIVs.length; i++) {
			if (i < numbers) {
				pinIVs[i].setBackgroundTintList(ColorStateList.valueOf(getColorFromAttribute(R.attr.vg_primaryDark)));
			} else {
				pinIVs[i].setBackgroundTintList(ColorStateList.valueOf(getColorFromAttribute(R.attr.vg_primaryLight)));
			}
		}
	}

	private int getColorFromAttribute(int attr) {
		return ContextCompat.getColor(this, UIUtils.resolveAttr(attr, this));
	}

}

