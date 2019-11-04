package vergecurrency.vergewallet.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

import com.google.gson.GsonBuilder

import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException

import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.ArrayList
import java.util.Arrays

import vergecurrency.vergewallet.Constants
import vergecurrency.vergewallet.service.model.Transaction

class TransactionsViewModel(application: Application) : AndroidViewModel(application) {

    //TODO : Catch Exception properly
    val transactionsList: ArrayList<Transaction>?
        get() {

            val parser = JSONParser()

            try {
                val `is` = getApplication<Application>().baseContext.assets.open(Constants.MOCK_TRANSACTIONS_FILE_PATH)

                val jsonObject = parser.parse(InputStreamReader(`is`)) as JSONObject
                val transactionsListJSON = jsonObject["transactions"] as JSONArray?

                val txsArray = GsonBuilder().create().fromJson(transactionsListJSON!!.toJSONString(), Array<Transaction>::class.java)
                return ArrayList(Arrays.asList(*txsArray))
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            } catch (e: ParseException) {
                e.printStackTrace()
                return null
            }

        }

    val balance: Double
        get() = 10.0
}
