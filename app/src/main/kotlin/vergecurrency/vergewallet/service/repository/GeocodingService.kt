package vergecurrency.vergewallet.service.repository

import cz.msebera.android.httpclient.client.methods.HttpGet
import org.json.JSONException
import org.json.JSONObject
import vergecurrency.vergewallet.Constants
import vergecurrency.vergewallet.service.model.network.layers.NetworkGateway

object GeocodingService {

    fun readCoordinates(ipAddress: String): String {

        var rawData: String = NetworkGateway().doRequest(HttpGet(String.format(Constants.IP_DATA_ENDPOINT, ipAddress)))

        if (rawData != "") {
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
