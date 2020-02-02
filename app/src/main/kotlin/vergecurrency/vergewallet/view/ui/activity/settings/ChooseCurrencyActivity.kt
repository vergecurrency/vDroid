package vergecurrency.vergewallet.view.ui.activity.settings

import android.os.Bundle
import android.widget.ListView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration

import vergecurrency.vergewallet.view.base.BaseActivity
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.service.model.Currency
import vergecurrency.vergewallet.service.model.PreferencesManager
import vergecurrency.vergewallet.view.adapter.CurrenciesAdapter
import vergecurrency.vergewallet.viewmodel.SelectCurrencyViewModel

class ChooseCurrencyActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_change)

        val mViewModel = ViewModelProviders.of(this).get(SelectCurrencyViewModel::class.java)

        val currenciesList = findViewById<ListView>(R.id.activity_select_currency_listview)
        currenciesList.adapter = CurrenciesAdapter(this, mViewModel.currencies!!)
        //currenciesList.setDivider();

    }
}
