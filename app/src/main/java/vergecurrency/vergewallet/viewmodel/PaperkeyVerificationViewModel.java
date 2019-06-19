package vergecurrency.vergewallet.viewmodel;

import androidx.lifecycle.ViewModel;

import vergecurrency.vergewallet.service.model.MnemonicManager;
import vergecurrency.vergewallet.service.model.PreferencesManager;

public class PaperkeyVerificationViewModel extends ViewModel {
    private String[] seed;

    public PaperkeyVerificationViewModel() {
        seed = MnemonicManager.getMnemonicFromJSON(PreferencesManager.getMnemonic());
    }

    public String[] getSeed() {
        return seed;

    }

    public void setFirstLaunch(boolean isFirstLaunch) {
        PreferencesManager.setFirstLaunch(isFirstLaunch);
    }
}
