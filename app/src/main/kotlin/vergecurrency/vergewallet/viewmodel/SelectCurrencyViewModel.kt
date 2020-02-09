package vergecurrency.vergewallet.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import vergecurrency.vergewallet.helpers.utils.CurrenciesUtils
import vergecurrency.vergewallet.service.model.Currency
import java.util.*


class SelectCurrencyViewModel(application: Application) : AndroidViewModel(application) {

    val currencies: ArrayList<Currency>?

    init {

        currencies = CurrenciesUtils.loadCurrenciesFromFile(getApplication<Application>().applicationContext)
    }
}
