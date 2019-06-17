package vergecurrency.vergewallet.viewmodel;

import androidx.lifecycle.ViewModel;
import vergecurrency.vergewallet.service.model.PreferencesManager;

import static vergecurrency.vergewallet.wallet.VergeWalletApplication.PREFERENCES_MANAGER;

public class PassphraseVerificationViewModel extends ViewModel {

	public void setFirstLaunch(boolean isFirstLaunch) {
		PREFERENCES_MANAGER.setFirstLaunch(isFirstLaunch);
	}

	public void setPassphrase(String passphrase) {PREFERENCES_MANAGER.setPassphrase(passphrase);}
}
