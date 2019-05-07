package vergecurrency.vergewallet.viewmodel;

import androidx.lifecycle.ViewModel;
import vergecurrency.vergewallet.service.model.MnemonicSeed;
import vergecurrency.vergewallet.service.model.PreferencesManager;

public class PassphraseVerificationViewModel extends ViewModel {

	PreferencesManager pm;

	public PassphraseVerificationViewModel() {
		pm = PreferencesManager.getInstance();

	}

	public void setFirstLaunch(boolean isFirstLaunch) {
		pm.setFirstLaunch(isFirstLaunch);
	}
}
