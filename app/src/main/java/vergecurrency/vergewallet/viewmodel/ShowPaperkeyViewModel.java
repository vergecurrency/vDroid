package vergecurrency.vergewallet.viewmodel;

import androidx.lifecycle.ViewModel;
import vergecurrency.vergewallet.service.model.MnemonicManager;
import vergecurrency.vergewallet.service.model.PreferencesManager;

public class ShowPaperkeyViewModel extends ViewModel {

	PreferencesManager pm;
	String[] seed;

	public ShowPaperkeyViewModel() {
		pm = PreferencesManager.getInstance();
		seed = MnemonicManager.getMnemonicFromJSON(pm.getMnemonic());

	}


	public String[] getSeed() {
		return seed;
	}

	public void setSeed(String[] seed) {
		this.seed = seed;
	}

	public String getSeedAsText() {
		StringBuilder result = new StringBuilder();
		for (String s:seed) {
			result.append(s).append(" ");
		}
		return result.toString();
	}

}
