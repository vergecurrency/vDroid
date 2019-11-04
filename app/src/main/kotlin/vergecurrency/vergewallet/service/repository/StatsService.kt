package vergecurrency.vergewallet.service.repository

import com.google.gson.Gson
import vergecurrency.vergewallet.Constants
import vergecurrency.vergewallet.service.model.ChartInfo
import vergecurrency.vergewallet.service.model.network.layers.ClearnetGateway
import java.util.*

object StatsService {

    fun readPriceStatistics(filter: Int): ChartInfo? {
        val rawData: String
        var result: ChartInfo?
        try {
            val request = String.format("%s%s", Constants.CHART_DATA_ENDPOINT, getUnixTimeframe(filter))
            rawData = ClearnetGateway().execute(request).get()
            result = Gson().fromJson(rawData, ChartInfo::class.java)
        } catch (e: Exception) {
            result = null
        }

        return result
    }


    fun getUnixTimeframe(filter: Int): String {
        val c = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        val now = "/" + System.currentTimeMillis()
        when (filter) {
            //One day
            1 -> {
                c.add(Calendar.DAY_OF_YEAR, -1)
                return c.timeInMillis.toString() + now
            }
            //One week
            2 -> {
                c.add(Calendar.WEEK_OF_YEAR, -1)
                return c.timeInMillis.toString() + now
            }
            //One month
            3 -> {
                c.add(Calendar.MONTH, -1)
                return c.timeInMillis.toString() + now
            }
            //Three months
            4 -> {
                c.add(Calendar.MONTH, -3)
                return c.timeInMillis.toString() + now
            }
            //One year
            5 -> {
                c.add(Calendar.YEAR, -1)
                return c.timeInMillis.toString() + now
            }
            //all time
            6 -> return ""
            else -> return ""
        }
    }
}



