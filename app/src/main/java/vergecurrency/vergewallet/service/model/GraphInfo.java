package vergecurrency.vergewallet.service.model;

import java.util.Map;

public class GraphInfo {

	public Map<String, String> getMarket_cap_by_available_supply() {
		return market_cap_by_available_supply;
	}

	public void setMarket_cap_by_available_supply(Map<String, String> market_cap_by_available_supply) {
		this.market_cap_by_available_supply = market_cap_by_available_supply;
	}

	public Map<String, String> getPrice_btc() {
		return price_btc;
	}

	public void setPrice_btc(Map<String, String> price_btc) {
		this.price_btc = price_btc;
	}

	public Map<String, String> getPrice_usd() {
		return price_usd;
	}

	public void setPrice_usd(Map<String, String> price_usd) {
		this.price_usd = price_usd;
	}

	public Map<String, String> getVolume_usd() {
		return volume_usd;
	}

	public void setVolume_usd(Map<String, String> volume_usd) {
		this.volume_usd = volume_usd;
	}

	private Map<String,String> market_cap_by_available_supply;
	private Map<String,String> price_btc;
	private Map<String,String> price_usd;
	private Map<String,String> volume_usd;

}
