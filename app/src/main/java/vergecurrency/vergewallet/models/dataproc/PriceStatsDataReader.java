package vergecurrency.vergewallet.models.dataproc;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import vergecurrency.vergewallet.Constants;
import vergecurrency.vergewallet.models.net.layers.ClearnetGateway;
import vergecurrency.vergewallet.models.net.layers.TorLayerGateway;
import vergecurrency.vergewallet.structs.PriceStatistic;

public final class PriceStatsDataReader {

	public PriceStatsDataReader() {

	}

	public static Map<String,String> readPriceStatistics(String currency) {
		/*
		TorLayerGateway tlg = TorLayerGateway.getInstance();
		String rawData = tlg.retrieveDataFromService(String.format("%s%s", Constants.PRICE_DATA_ENDPOINT, currency)); */
		ClearnetGateway clearnetGateway = ClearnetGateway.getInstance();
		String rawData = null;
		try {
			rawData = clearnetGateway.execute(String.format("%s%s", Constants.PRICE_DATA_ENDPOINT, currency)).get();
		} catch (Exception e) {
			rawData = "error";
		}

		Type stringStringMap = new TypeToken<Map<String, String>>(){}.getType();
		return new Gson().fromJson(rawData, stringStringMap);
	}
}
