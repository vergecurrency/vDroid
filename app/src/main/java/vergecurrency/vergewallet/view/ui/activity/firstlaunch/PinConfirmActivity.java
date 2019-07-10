package vergecurrency.vergewallet.view.ui.activity.firstlaunch;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.davidmiguel.numberkeyboard.NumberKeyboard;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.view.base.BaseActivity;
public class PinConfirmActivity extends BaseActivity {

	private NumberKeyboard numberKeyboard;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_confirm_wallet_pin);
		initComponents();
	}

	private void initComponents() {

		numberKeyboard = findViewById(R.id.pin_number_pad);

		//numberKeyboard.setLeftAuxButtonIcon(R.drawable.icon_fingerprint);
		numberKeyboard.setRightAuxButtonIcon(R.drawable.icon_backspace);

	}


}

