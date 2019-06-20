package vergecurrency.vergewallet.viewmodel;

import androidx.lifecycle.ViewModel;
import vergecurrency.vergewallet.service.model.PreferencesManager;


public class PassphraseVerificationViewModel extends ViewModel {

	public void setFirstLaunch(boolean isFirstLaunch) {
		PreferencesManager.setFirstLaunch(isFirstLaunch);
	}

	public void setPassphrase(String passphrase) {PreferencesManager.setPassphrase(passphrase);}
}
