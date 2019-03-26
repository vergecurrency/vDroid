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
		if(!pm.getFirstLaunch()) {
			seed = MnemonicSeed.getSeedFromJson(pm.getMnemonic());
		}

	}

	public String[] getSeed() throws Exception {

		return seed;
	}

	public void setSeed(String[] seed) {
		this.seed = seed;
	}

	public void generateMnemonics() {
		//Should be created at the launch
		wm.generateSeed();
		seed = MnemonicSeed.getSeedFromJson(pm.getMnemonic());
	}
}
