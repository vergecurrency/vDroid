package vergecurrency.vergewallet.service.repository

import org.json.JSONException
import org.json.JSONObject

//TODO : Query should be done here
//TODO : Make it a static class
object GeocodingService {

    fun readCoordinates(rawData: String?): String {


        if (rawData != null && rawData != "") {
            val reader: JSONObject
            try {
                reader = JSONObject(rawData)
                val lat = reader.getString("latitude")
                val lon = reader.getString("longitude")
                return String.format("%s;%s", lat, lon)
            } catch (e: JSONException) {
                e.printStackTrace()
                return "error"
            }

        } else
            return "error"
    }
}
