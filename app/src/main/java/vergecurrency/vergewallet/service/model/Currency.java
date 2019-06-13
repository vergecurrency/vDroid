package vergecurrency.vergewallet.service.model;

import com.google.gson.Gson;

public class Currency {

	private String currency;
	private String name;

	public Currency() {

	}

	public Currency(String name, String currency) {
		this.name = name;
		this.currency = currency;
	}

	public static Currency getCurrencyFromJson(String json) {
		return new Gson().fromJson(json, Currency.class);
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

	public String getCurrencyAsJSON() {
		return new Gson().toJson(this);
	}
}
