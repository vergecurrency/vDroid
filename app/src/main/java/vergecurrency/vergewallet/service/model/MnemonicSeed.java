package vergecurrency.vergewallet.service.model;

import com.google.gson.Gson;

public class MnemonicSeed {


	private String[] seed;

	public static String[] getSeedFromJson(String seed) {
		return new Gson().fromJson(seed, String[].class);
	}

	public String getSeedAsJson() {
		return new Gson().toJson(this);
	}

	public String[] getSeed() {
		return seed;
	}

	public void setSeed(String[] seed) {
		this.seed = seed;
	}
}
