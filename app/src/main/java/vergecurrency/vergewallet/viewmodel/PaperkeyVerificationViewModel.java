package vergecurrency.vergewallet.viewmodel;

import androidx.lifecycle.ViewModel;

import vergecurrency.vergewallet.service.model.MnemonicManager;
import vergecurrency.vergewallet.service.model.PreferencesManager;

public class PaperkeyVerificationViewModel extends ViewModel {

	private PreferencesManager pm;
	private String[] seed;

	public PaperkeyVerificationViewModel() {
		pm = PreferencesManager.getInstance();
		seed = MnemonicManager.getMnemonicFromJSON(pm.getMnemonic());
	}

	public String[] getSeed() {
		return seed;

	}

	public void setFirstLaunch(boolean isFirstLaunch) {
		pm.setFirstLaunch(isFirstLaunch);
	}
}
