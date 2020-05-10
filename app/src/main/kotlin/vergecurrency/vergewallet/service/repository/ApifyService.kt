package vergecurrency.vergewallet.service.repository

import cz.msebera.android.httpclient.client.methods.HttpGet
import org.json.JSONException
import org.json.JSONObject
import vergecurrency.vergewallet.Constants
import vergecurrency.vergewallet.service.model.network.layers.NetworkGateway

//TODO : Query should be done here
object ApifyService {

    //to be moved into a apify parser
    fun requestIP(): String {

        var rawData: String = NetworkGateway().doRequest(HttpGet(Constants.IP_RETRIEVAL_ENDPOINT))

        if (rawData != "") {
            val reader: JSONObject
            try {
                reader = JSONObject(rawData)
                return reader.getString("ip")
            } catch (e: JSONException) {
                e.printStackTrace()
                return "error"
            }

        } else
            return "error"
    }
}
