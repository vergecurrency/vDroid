package vergecurrency.vergewallet.viewmodel


import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.Entry
import vergecurrency.vergewallet.service.model.ChartInfo
import vergecurrency.vergewallet.service.repository.StatsService
import java.util.*

class ChartsViewModel : ViewModel() {

    var gi: ChartInfo? = null

    fun retrieveData(filter: Int) {
        gi = StatsService.readPriceStatistics(filter)
    }

    fun getVolumeData(): List<Entry> {
        return createEntryList(gi!!.volume_usd!!.entries)
    }

    fun getPriceData(): List<Entry> {
        return createEntryList(gi!!.price_usd!!.entries)
    }


    private fun createEntryList(set: Set<Map.Entry<String, String>>): List<Entry> {
        val entries = ArrayList<Entry>()
        var counter = 0f
        for (e in set) {
            entries.add(Entry(counter, java.lang.Float.parseFloat(e.value)))
            counter++
        }

        return entries
    }
}
