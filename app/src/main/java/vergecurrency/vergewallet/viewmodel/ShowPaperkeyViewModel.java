package vergecurrency.vergewallet.viewmodel;

import androidx.lifecycle.ViewModel;

import vergecurrency.vergewallet.service.model.MnemonicManager;

import static vergecurrency.vergewallet.service.model.PreferencesManager.getMnemonic;

public class ShowPaperkeyViewModel extends ViewModel {
	String[] seed;

	public ShowPaperkeyViewModel() {
		seed = MnemonicManager.getMnemonicFromJSON(getMnemonic());

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
