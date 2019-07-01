package vergecurrency.vergewallet.view.ui.activity.settings;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import vergecurrency.vergewallet.view.base.BaseActivity;
import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.view.adapter.CurrenciesAdapter;
import vergecurrency.vergewallet.viewmodel.SelectCurrencyViewModel;

public class ChooseCurrencyActivity extends BaseActivity {


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_currency_change);

		SelectCurrencyViewModel mViewModel = ViewModelProviders.of(this).get(SelectCurrencyViewModel.class);

		ListView currenciesList = findViewById(R.id.activity_select_currency_listview);
		currenciesList.setAdapter(new CurrenciesAdapter(this, mViewModel.getCurrencies()));

	}
}
