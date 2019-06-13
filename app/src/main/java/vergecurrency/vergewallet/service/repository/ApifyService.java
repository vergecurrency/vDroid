package vergecurrency.vergewallet.service.repository;

import org.json.JSONException;
import org.json.JSONObject;

public class ApifyService {

	//TODO : Query should be done here
	//TODO : Make it a static class
	public ApifyService() {
	}

	//to be moved into a apify parser
	public String readIP(String rawData) {

		if (rawData != null && !rawData.equals("")) {
			JSONObject reader;
			try {
				reader = new JSONObject(rawData);
				return reader.getString("ip");
			} catch (JSONException e) {
				e.printStackTrace();
				return "error";
			}
		} else return "error";
	}
}
