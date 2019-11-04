package vergecurrency.vergewallet.service.repository

import org.json.JSONException
import org.json.JSONObject

//TODO : Query should be done here
object ApifyService {

    //to be moved into a apify parser
    fun readIP(rawData: String?): String {

        if (rawData != null && rawData != "") {
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
