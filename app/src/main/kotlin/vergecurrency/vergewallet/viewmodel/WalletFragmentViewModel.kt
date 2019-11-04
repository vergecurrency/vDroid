package vergecurrency.vergewallet.viewmodel

import androidx.lifecycle.ViewModel
import vergecurrency.vergewallet.service.model.Currency
import vergecurrency.vergewallet.service.model.PreferencesManager
import vergecurrency.vergewallet.service.repository.RatesClient
import vergecurrency.vergewallet.wallet.WalletManager

class WalletFragmentViewModel : ViewModel() {
    var balance: Long? = null
    //TODO check if necessary
    private val currencyChange: String?

    val currencyCode: String
        get() = Currency.getCurrencyFromJson(PreferencesManager.preferredCurrency!!).currency!!

    init {
        balance = WalletManager.getBalance().value
        //TODO check if necessary
        currencyChange = RatesClient.readPriceStatistics(currencyCode)["XVG/USD"]
    }
}