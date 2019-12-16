package vergecurrency.vergewallet.viewmodel


import android.app.Application

import java.util.ArrayList
import androidx.lifecycle.AndroidViewModel
import vergecurrency.vergewallet.service.model.Currency
import vergecurrency.vergewallet.helpers.utils.CurrenciesUtils


class SelectCurrencyViewModel(application: Application) : AndroidViewModel(application) {

    val currencies: ArrayList<Currency>?

    init {

        currencies = CurrenciesUtils.loadCurrenciesFromFile(getApplication<Application>().applicationContext)
    }
}
