package vergecurrency.vergewallet.viewmodel;

import androidx.lifecycle.ViewModel;
import vergecurrency.vergewallet.service.model.MnemonicSeed;
import vergecurrency.vergewallet.service.model.PreferencesManager;

public class PaperkeyVerificationViewModel extends ViewModel {

	PreferencesManager pm;
	private String[] seed;

	public PaperkeyVerificationViewModel() {
		pm = PreferencesManager.getInstance();

		seed = MnemonicSeed.getSeedFromJson(pm.getMnemonic());
	}

	public String[] getSeed() {
		return seed;

	}

	public void setFirstLaunch(boolean isFirstLaunch) {
		pm.setFirstLaunch(isFirstLaunch);
	}
}
