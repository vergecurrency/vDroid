package vergecurrency.vergewallet.viewmodel


import com.github.mikephil.charting.data.Entry

import java.util.ArrayList

import androidx.lifecycle.ViewModel
import vergecurrency.vergewallet.service.repository.StatsService

class ChartsViewModel : ViewModel() {


    fun getVolumeData(filter: Int): List<Entry> {
        val gi = StatsService.readPriceStatistics(filter)
        return createEntryList(gi!!.volume_usd!!.entries)
    }

    fun getPriceData(filter: Int): List<Entry> {
        val gi = StatsService.readPriceStatistics(filter)
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
