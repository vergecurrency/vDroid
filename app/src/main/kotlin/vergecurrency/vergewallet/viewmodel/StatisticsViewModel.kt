package vergecurrency.vergewallet.viewmodel


import java.util.ArrayList

import androidx.lifecycle.ViewModel
import vergecurrency.vergewallet.service.model.Currency
import vergecurrency.vergewallet.service.repository.RatesClient

import vergecurrency.vergewallet.service.model.PreferencesManager.Companion.preferredCurrency

class StatisticsViewModel : ViewModel() {

    private val currencyCode: String
    val statistics: ArrayList<Map.Entry<String, String>>

    init {
        currencyCode = Currency.getCurrencyFromJson(preferredCurrency!!).currency!!
        statistics = ArrayList(RatesClient.readPriceStatistics(currencyCode).entries)
    }


}
