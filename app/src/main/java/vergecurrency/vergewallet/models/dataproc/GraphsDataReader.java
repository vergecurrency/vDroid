package vergecurrency.vergewallet.models.dataproc;

import com.google.gson.Gson;

import vergecurrency.vergewallet.Constants;
import vergecurrency.vergewallet.models.net.layers.ClearnetGateway;
import vergecurrency.vergewallet.structs.GraphInfo;

public class GraphsDataReader {


	public static GraphInfo readPriceStatistics() {
		String rawData = null;
		GraphInfo result;
		try {
			rawData = new ClearnetGateway().execute(String.format("%s", Constants.CHART_DATA_ENDPOINT)).get();
			result = new Gson().fromJson(rawData,GraphInfo.class);
		} catch (Exception e) {
			rawData = "error";
			result = null;
		}

		return result;
	}



}
