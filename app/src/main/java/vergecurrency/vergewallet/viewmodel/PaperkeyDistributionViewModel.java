package vergecurrency.vergewallet.viewmodel;

import androidx.lifecycle.ViewModel;
import vergecurrency.vergewallet.service.model.MnemonicSeed;
import vergecurrency.vergewallet.service.model.PreferencesManager;
import vergecurrency.vergewallet.wallet.WalletManager;

public class PaperkeyDistributionViewModel extends ViewModel {

	String[] seed;
	PreferencesManager pm;
	private WalletManager wm;

	public PaperkeyDistributionViewModel() {
		wm = new WalletManager();
		pm = PreferencesManager.getInstance();
	}

	public String[] getSeed() throws Exception {
		seed = MnemonicSeed.getSeedFromJson(pm.getMnemonic());
		if (seed != null) {
			return null;
		} else throw new Exception();
	}

	public void generateMnemonics() {
		//Should be created at the launch
		wm.generateSeed();

	}
}
