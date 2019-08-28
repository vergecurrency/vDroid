package vergecurrency.vergewallet.view.ui.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import com.davidmiguel.numberkeyboard.NumberKeyboard;
import com.davidmiguel.numberkeyboard.NumberKeyboardListener;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.helpers.utils.UIUtils;
import vergecurrency.vergewallet.view.base.BaseActivity;
import vergecurrency.vergewallet.view.ui.activity.firstlaunch.PinSetActivity;
import vergecurrency.vergewallet.view.ui.activity.settings.PaperkeyActivity;
import vergecurrency.vergewallet.viewmodel.PinPromptedViewModel;

public class PinPromptActivity extends BaseActivity {

	private String nextView;
	private String pin;
	private ImageView[] pinIVs;
	private PinPromptedViewModel mViewModel;
	private GridLayout pinLayout;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		nextView = getIntent().getStringExtra("nextView");
		mViewModel = ViewModelProviders.of(this).get(PinPromptedViewModel.class);

		setContentView(R.layout.activity_pin_prompt);
		initComponents();

	}

	private void initComponents() {
		pin = "";

		pinLayout = findViewById(R.id.pin_digits_prompt);

		pinIVs = new ImageView[6];
		//As a second priority, make this dynamic to allow for pin size change
		pinIVs[0] = findViewById(R.id.pin_one_prompt);
		pinIVs[1] = findViewById(R.id.pin_two_prompt);
		pinIVs[2] = findViewById(R.id.pin_three_prompt);
		pinIVs[3] = findViewById(R.id.pin_four_prompt);
		pinIVs[4] = findViewById(R.id.pin_five_prompt);
		pinIVs[5] = findViewById(R.id.pin_six_prompt);

		NumberKeyboard numberKeyboard = findViewById(R.id.pin_number_pad);

		numberKeyboard.setLeftAuxButtonIcon(R.drawable.icon_fingerprint);
		numberKeyboard.setRightAuxButtonIcon(R.drawable.icon_backspace);

		numberKeyboard.setListener(nkl());

	}

	private NumberKeyboardListener nkl() {
		return new NumberKeyboardListener() {
			@Override
			public void onNumberClicked(int i) {
				pin = pin.concat(i + "");
				changePinColors(pin.length());

				if (pin.length() == 6) {
					if (pin.equals(mViewModel.getPin())) {
						redirectView();
					} else {
						Toast.makeText(getApplicationContext(), "The pin inserted was wrong. Start Over", Toast.LENGTH_SHORT).show();
						pinLayout.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake));
						pin = "";
						changePinColors(pin.length());
					}
				}
			}

			private void redirectView() {
				finish();
				switch (nextView) {
					case "viewPassphrase":
						startActivity(new Intent(getApplicationContext(), PaperkeyActivity.class));
						break;
					case "performTransaction":
						break;
					case "wallet":
						startActivity(new Intent(getApplicationContext(), WalletActivity.class));
						break;
					case "changePin":
						Intent i = new Intent(getApplicationContext(), PinSetActivity.class);
						i.putExtra("origin", "settings");
						startActivity(i);
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

