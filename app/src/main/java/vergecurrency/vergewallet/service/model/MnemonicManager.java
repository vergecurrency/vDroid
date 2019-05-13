package vergecurrency.vergewallet.service.model;

import com.google.gson.Gson;

public class MnemonicManager {


	private String[] mnemonic;

	public static String[] getMnemonicFromJSON(String mnemonic) {
		return new Gson().fromJson(mnemonic, String[].class);
	}

	public String getMnemonicAsJSON() {
		return new Gson().toJson(this.mnemonic);
	}

	public String[] getMnemonic() {
		return mnemonic;
	}

	public void setMnemonic(String[] mnemonic) {
		this.mnemonic = mnemonic;
	}
}
