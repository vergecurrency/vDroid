package vergecurrency.vergewallet.structs;

import com.google.gson.Gson;

public class Currency {

public Currency() {

}

public Currency(String name, String currency) {
	this.name = name;
	this.currency = currency;
	this.symbol = symbol;
}


	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}



	//Those functions are needed to store and retrieve the Currency object in shared preferences.

	public String getCurrencyAsJSON() {
		return new Gson().toJson(this);
	}

	public static Currency getCurrencyFromJson(String json) {
		return new Gson().fromJson(json, Currency.class);
	}

	private String currency;
	private String name;
	private String symbol;
}
