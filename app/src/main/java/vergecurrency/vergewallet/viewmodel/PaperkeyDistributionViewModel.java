package vergecurrency.vergewallet.viewmodel;

import androidx.lifecycle.ViewModel;
import vergecurrency.vergewallet.service.model.MnemonicManager;
import vergecurrency.vergewallet.service.model.PreferencesManager;
import vergecurrency.vergewallet.wallet.WalletManager;

public class PaperkeyDistributionViewModel extends ViewModel {

    String[] seed;

    public PaperkeyDistributionViewModel() {
        if(!PreferencesManager.isFirstLaunch()) {
            seed = MnemonicManager.getMnemonicFromJSON(PreferencesManager.getMnemonic());
        }

    }

    public String[] getSeed() {

        return seed;
    }

    public void setSeed(String[] seed) {
        this.seed = seed;
    }

    public void generateMnemonics() {
        //Should be created at the launch
        WalletManager.generateMnemonic();
        seed = MnemonicManager.getMnemonicFromJSON(PreferencesManager.getMnemonic());
    }
}
