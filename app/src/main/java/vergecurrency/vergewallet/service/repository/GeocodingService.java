package vergecurrency.vergewallet.service.repository;

import org.json.JSONException;
import org.json.JSONObject;

public class GeocodingService {

    public GeocodingService() {}

    public String readCoordinates(String rawData) {

        String lat ="";
        String lon = "";


        if (rawData != null && !rawData.equals("")) {
            JSONObject reader;
            try {
                reader = new JSONObject(rawData);
                lat =  reader.getString("latitude");
                lon =  reader.getString("longitude");
                return String.format("%s;%s", lat, lon);
            } catch (JSONException e) {
                e.printStackTrace();
                return "error";
            }
        } else return "error";
    }
}
