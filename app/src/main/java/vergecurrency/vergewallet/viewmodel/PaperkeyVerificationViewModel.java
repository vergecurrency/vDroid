package vergecurrency.vergewallet.viewmodel;

import androidx.lifecycle.ViewModel;

import vergecurrency.vergewallet.service.model.MnemonicManager;

import static vergecurrency.vergewallet.wallet.VergeWalletApplication.PREFERENCES_MANAGER;

public class PaperkeyVerificationViewModel extends ViewModel {
    private String[] seed;

	public PaperkeyVerificationViewModel() {
		seed = MnemonicManager.getMnemonicFromJSON(PREFERENCES_MANAGER.getMnemonic());
	}

	public String[] getSeed() {
		return seed;

	}

	public void setFirstLaunch(boolean isFirstLaunch) {
        PREFERENCES_MANAGER.setFirstLaunch(isFirstLaunch);
	}
}
