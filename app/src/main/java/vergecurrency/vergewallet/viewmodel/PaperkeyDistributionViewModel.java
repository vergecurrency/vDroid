package vergecurrency.vergewallet.viewmodel;

import androidx.lifecycle.ViewModel;
import vergecurrency.vergewallet.service.model.MnemonicManager;
import vergecurrency.vergewallet.service.model.PreferencesManager;
import vergecurrency.vergewallet.wallet.WalletManager;

public class PaperkeyDistributionViewModel extends ViewModel {

	String[] seed;
	PreferencesManager pm;
	private WalletManager wm;

	public PaperkeyDistributionViewModel() {
		wm = WalletManager.init();
		pm = PreferencesManager.getInstance();
		if(!pm.getFirstLaunch()) {
			seed = MnemonicManager.getMnemonicFromJSON(pm.getMnemonic());
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
		wm.generateMnemonic();
		seed = MnemonicManager.getMnemonicFromJSON(pm.getMnemonic());
	}
}
