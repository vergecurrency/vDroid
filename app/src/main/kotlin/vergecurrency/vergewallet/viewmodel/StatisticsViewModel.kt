package vergecurrency.vergewallet.viewmodel


import androidx.lifecycle.ViewModel
import vergecurrency.vergewallet.service.model.Currency
import vergecurrency.vergewallet.service.model.EncryptedPreferencesManager.Companion.preferredCurrency
import vergecurrency.vergewallet.service.repository.RatesClient
import java.util.*

class StatisticsViewModel : ViewModel() {

    private val currencyCode: CharArray
    val statistics: ArrayList<Map.Entry<String, String>>

    init {
        currencyCode = Currency.getCurrencyFromJson(preferredCurrency!!).currency!!
        statistics = ArrayList(RatesClient.readPriceStatistics(currencyCode).entries)
    }


}
