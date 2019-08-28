package vergecurrency.vergewallet.view.ui.activity.firstlaunch;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.davidmiguel.numberkeyboard.NumberKeyboard;
import com.davidmiguel.numberkeyboard.NumberKeyboardListener;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.helpers.utils.UIUtils;
import vergecurrency.vergewallet.view.base.BaseActivity;

public class PinSetActivity extends BaseActivity {

	private NumberKeyboard numberKeyboard;

	private ImageView[] pinIVs;

	private String pin;
	private String origin;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		origin = getIntent().getStringExtra("origin");

		setContentView(R.layout.activity_pin_set);
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

				//replace with preferences manager pinCount() once implemented feature
				if (pin.length() == 6) {
					finish();
					Intent intent = new Intent(getApplicationContext(), PinConfirmActivity.class);
					intent.putExtra("pin",pin);
					intent.putExtra("origin", origin);
					startActivity(intent);
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

