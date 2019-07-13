package vergecurrency.vergewallet.view.ui.activity.firstlaunch;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.davidmiguel.numberkeyboard.NumberKeyboard;
import com.davidmiguel.numberkeyboard.NumberKeyboardListener;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.service.model.PreferencesManager;
import vergecurrency.vergewallet.view.base.BaseActivity;
public class PinSetActivity extends BaseActivity {

	private NumberKeyboard numberKeyboard;
	private ImageView ivPinOne;
	private ImageView ivPinTwo;
	private ImageView ivPinThree;
	private ImageView ivPinFour;
	private ImageView ivPinFive;
	private ImageView ivPinSix;

	private ImageView[] pinIVs;

	private String pin;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_wallet_set_pin);
		initComponents();
	}

	private void initComponents() {
		pin = "";

		pinIVs = new ImageView[6];
		//As a second priority, make this dynamic to allow for pin size change
		pinIVs[0] = findViewById(R.id.pin_one);
		pinIVs[1] = findViewById(R.id.pin_two);
		pinIVs[2] = findViewById(R.id.pin_three);
		pinIVs[3] = findViewById(R.id.pin_four);
		pinIVs[4] = findViewById(R.id.pin_five);
		pinIVs[5] = findViewById(R.id.pin_six);

		numberKeyboard = findViewById(R.id.pin_number_pad);

		//numberKeyboard.setLeftAuxButtonIcon(R.drawable.icon_fingerprint);
		numberKeyboard.setRightAuxButtonIcon(R.drawable.icon_backspace);

	}

	private NumberKeyboardListener nkl() {
		return new NumberKeyboardListener() {
			@Override
			public void onNumberClicked(int i) {
				pin = pin.concat(i + "");
				changePinColors(pin.length());

				//replace with preferences manager pinCount() once implemented feature
				if (pin.length() == 6) {
					//go to next activity. will do tomorrow bitch.
				}
			}

			@Override
			public void onLeftAuxButtonClicked() {
				Toast.makeText(getApplicationContext(), "Fingerprint not available yet.", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onRightAuxButtonClicked() {

			}
		};
	}

	private void changePinColors(int numbers) {

	}

}

