package vergecurrency.vergewallet.service.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.Map;

import vergecurrency.vergewallet.Constants;
import vergecurrency.vergewallet.utilities.MathUtils;
import vergecurrency.vergewallet.service.model.network.layers.ClearnetGateway;

public final class PriceStatsDataReader {

	public PriceStatsDataReader() {

	}

	public static Map<String, String> readPriceStatistics(String currency) {
		/*
		TorLayerGateway tlg = TorLayerGateway.getInstance();
		String rawData = tlg.retrieveDataFromService(String.format("%s%s", Constants.PRICE_DATA_ENDPOINT, currency)); */

		String rawData = null;
		try {
			rawData = new ClearnetGateway().execute(String.format("%s%s", Constants.PRICE_DATA_ENDPOINT, currency)).get();
		} catch (Exception e) {
			rawData = "error";
		}

		Type stringStringMap = new TypeToken<Map<String, String>>() {
		}.getType();
		Map<String, String> valuesMap = new Gson().fromJson(rawData, stringStringMap);


		return reworkMap(valuesMap, currency);
	}


	private static Map<String, String> reworkMap(Map<String, String> map, String currency) {
		Map<String, String> arrangedMap = arrangeItems(map);
		Map<String, String> formattedData = formatItems(arrangedMap, currency);


		return formattedData;
	}

	private static Map<String, String> arrangeItems(Map<String, String> map) {
		map.remove("__v");
		map.remove("changeday");
		map.remove("changepctday");
		map.remove("highday");
		map.remove("lowday");
		map.remove("open24Hour");
		map.remove("openday");
		map.remove("rank");
		map.remove("supply");
		map.put("XVG/USD", map.remove("price"));
		map.put("Market Cap", map.remove("mktcap"));
		map.put("24h Change %", map.remove("changepct24Hour"));
		map.put("24h High", map.remove("high24Hour"));
		map.put("24h Low", map.remove("low24Hour"));
		map.put("24h Change", map.remove("change24Hour"));
		map.put("24h Volume XVG", map.remove("totalvolume24H"));
		map.put("24h Volume", map.remove("totalvolume24Hto"));

		return map;
	}

	private static Map<String, String> formatItems(Map<String, String> map, String currency) {

		map.put("XVG/USD", currency + " " + roundSmall(map.get("XVG/USD"), 6));

		map.put("Market Cap", currency + " " + roundBig(map.get("Market Cap"), 0));
		map.put("24h Change %", roundSmall(map.get("24h Change %"), 2) + "%");
		map.put("24h High", currency + " " + roundSmall(map.get("24h High"), 6));
		map.put("24h Low", currency + " " + roundSmall(map.get("24h Low"), 6));
		map.put("24h Change", currency + " " + roundSmall(map.get("24h Change"), 6));
		map.put("24h Volume XVG", roundBig(map.get("24h Volume XVG"), 0) + " XVG");
		map.put("24h Volume", currency + " " + roundBig(map.get("24h Volume"), 0));


		return map;
	}

	private static String roundSmall(String value, int places) {
		Double result = MathUtils.round(Double.parseDouble(value), places);
		return new DecimalFormat("0.######").format(result);
	}

	private static String roundBig(String value, int places) {
		Double result = MathUtils.round(Double.parseDouble(value), places);
		return new DecimalFormat("##").format(result);
	}

}
