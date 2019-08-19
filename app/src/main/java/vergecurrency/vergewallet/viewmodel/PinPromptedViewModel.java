package vergecurrency.vergewallet.viewmodel;

import androidx.lifecycle.ViewModel;

import vergecurrency.vergewallet.service.model.PreferencesManager;

public class PinPromptedViewModel extends ViewModel {

	public PinPromptedViewModel(){

	}


	public String getPin() {
		return PreferencesManager.getPin();
	}
}
