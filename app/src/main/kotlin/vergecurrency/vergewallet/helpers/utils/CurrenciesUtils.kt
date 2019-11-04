package vergecurrency.vergewallet.helpers.utils

import android.content.Context

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
import vergecurrency.vergewallet.service.model.Currency

object CurrenciesUtils {



        fun loadCurrenciesFromFile(c: Context): ArrayList<Currency>? {
            val parser = JSONParser()
            var currencies: ArrayList<Currency>?
            try {
                val `is` = c.assets.open(Constants.CURRENCIES_FILE_PATH)
                val isr = InputStreamReader(`is`)
                val jsonObject = parser.parse(isr) as JSONObject
                val currenciesJSON = jsonObject["currencies"] as JSONArray?


                val currenciesArray: Array<Currency>
                currenciesArray = GsonBuilder().create().fromJson(currenciesJSON!!.toJSONString(), Array<Currency>::class.java)
                currencies = ArrayList(Arrays.asList(*currenciesArray))


            } catch (e: IOException) {
                currencies = null
                e.printStackTrace()
            } catch (e: ParseException) {
                e.printStackTrace()
                currencies = null
            }

            return currencies
        }
    }



