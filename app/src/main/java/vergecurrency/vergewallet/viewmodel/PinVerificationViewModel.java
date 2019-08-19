package vergecurrency.vergewallet.viewmodel;

import androidx.lifecycle.ViewModel;

import vergecurrency.vergewallet.service.model.PreferencesManager;

public class PinVerificationViewModel extends ViewModel {

	public PinVerificationViewModel(){

	}


	public void setPin(String pin) {
		PreferencesManager.setPin(pin);
	}
}
