package vergecurrency.vergewallet.viewmodel


import androidx.lifecycle.ViewModel
import vergecurrency.vergewallet.service.model.Currency
import vergecurrency.vergewallet.service.model.PreferencesManager
import vergecurrency.vergewallet.service.repository.RatesClient
import java.util.*

class StatisticsViewModel : ViewModel() {

    private val currencyCode: String
    val statistics: ArrayList<Map.Entry<String, String>>

    init {
        currencyCode = Currency.getCurrencyFromJson(PreferencesManager.preferredCurrency!!).currency!!
        statistics = ArrayList(RatesClient.readPriceStatistics(currencyCode).entries)
    }


}
