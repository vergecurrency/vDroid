package vergecurrency.vergewallet.viewmodel;

import androidx.lifecycle.ViewModel;

import vergecurrency.vergewallet.excpetion.DefaultExceptionHandler;
import vergecurrency.vergewallet.excpetion.VergeException;
import vergecurrency.vergewallet.service.model.MnemonicManager;
import vergecurrency.vergewallet.service.model.PreferencesManager;

import static vergecurrency.vergewallet.excpetion.VergeError.FAILED_TO_GENERATE_SEED;

public class PaperkeyVerificationViewModel extends ViewModel {

	PreferencesManager pm;
	private String[] seed;

	public PaperkeyVerificationViewModel() {
		pm = PreferencesManager.getInstance();

        Thread.setDefaultUncaughtExceptionHandler(DefaultExceptionHandler.getInstance());
		seed = MnemonicManager.getMnemonicFromJSON(pm.getMnemonic());
		DefaultExceptionHandler.getInstance().handle(new VergeException(FAILED_TO_GENERATE_SEED));

	}

	public String[] getSeed() {
		return seed;

	}

	public void setFirstLaunch(boolean isFirstLaunch) {
		pm.setFirstLaunch(isFirstLaunch);
	}
}
