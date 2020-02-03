package vergecurrency.vergewallet.service.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import vergecurrency.vergewallet.Constants
import vergecurrency.vergewallet.helpers.utils.MathUtils
import vergecurrency.vergewallet.service.model.FiatRate
import vergecurrency.vergewallet.service.model.network.layers.NetworkGateway
import java.text.DecimalFormat
import java.util.*

object RatesClient {


    fun infoBy(currency: String): FiatRate? {

        return try {
            val rawData = NetworkGateway().doRequest(String.format("%s%s", Constants.PRICE_DATA_ENDPOINT, currency))
            FiatRate.decode(rawData)

        } catch (e: Exception) {
            return FiatRate()
        }

    }

    fun readPriceStatistics(currency: String): Map<String, String> {
        var rawData: String
        try {
            rawData = NetworkGateway().doRequest(String.format("%s%s", Constants.PRICE_DATA_ENDPOINT, currency))
        } catch (e: Exception) {
            rawData = "error"
        }

        val stringStringMap = object : TypeToken<Map<String, String>>() {}.type
        val valuesMap = Gson().fromJson<MutableMap<String, String>>(rawData, stringStringMap)


        return reworkMap(valuesMap, currency)
    }


    private fun reworkMap(map: MutableMap<String, String>?, currency: String): Map<String, String> {
        val arrangedMap = arrangeItems(map!!)


        return formatItems(arrangedMap, currency)
    }

    private fun arrangeItems(map: MutableMap<String, String>): MutableMap<String, String> {
        map.remove("__v")
        map.remove("changeday")
        map.remove("changepctday")
        map.remove("highday")
        map.remove("lowday")
        map.remove("open24Hour")
        map.remove("openday")
        map.remove("rank")
        map.remove("supply")
        map["XVG/USD"] = Objects.requireNonNull<String>(map.remove("price"))
        map["Market Cap"] = Objects.requireNonNull<String>(map.remove("mktcap"))
        map["24h Change %"] = Objects.requireNonNull<String>(map.remove("changepct24Hour"))
        map["24h High"] = Objects.requireNonNull<String>(map.remove("high24Hour"))
        map["24h Low"] = Objects.requireNonNull<String>(map.remove("low24Hour"))
        map["24h Change"] = Objects.requireNonNull<String>(map.remove("change24Hour"))
        map["24h Volume XVG"] = Objects.requireNonNull<String>(map.remove("totalvolume24H"))
        map["24h Volume"] = Objects.requireNonNull<String>(map.remove("totalvolume24Hto"))

        return map
    }

    private fun formatItems(map: MutableMap<String, String>, currency: String): Map<String, String> {

        map["XVG/USD"] = currency + " " + roundSmall(map["XVG/USD"], 6)

        map["Market Cap"] = currency + " " + roundBig(map["Market Cap"], 0)
        map["24h Change %"] = roundSmall(map["24h Change %"], 2) + "%"
        map["24h High"] = currency + " " + roundSmall(map["24h High"], 6)
        map["24h Low"] = currency + " " + roundSmall(map["24h Low"], 6)
        map["24h Change"] = currency + " " + roundSmall(map["24h Change"], 6)
        map["24h Volume XVG"] = roundBig(map["24h Volume XVG"], 0) + " XVG"
        map["24h Volume"] = currency + " " + roundBig(map["24h Volume"], 0)


        return map
    }

    private fun roundSmall(value: String?, places: Int): String {
        val result = MathUtils.round(java.lang.Double.parseDouble(value!!), places)
        return DecimalFormat("0.######").format(result)
    }

    private fun roundBig(value: String?, places: Int): String {
        val result = MathUtils.round(java.lang.Double.parseDouble(value!!), places)
        return DecimalFormat("##").format(result)
    }
}


