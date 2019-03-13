package vergecurrency.vergewallet.view.ui.activity.settings;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.utilities.CurrenciesUtils;
import vergecurrency.vergewallet.view.adapter.CurrenciesAdapter;
import vergecurrency.vergewallet.viewmodel.SelectCurrencyViewModel;

public class SelectCurrencyActivity extends AppCompatActivity {


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_currency);

		SelectCurrencyViewModel mViewModel = ViewModelProviders.of(this).get(SelectCurrencyViewModel.class);

		ListView currenciesList = findViewById(R.id.activity_select_currency_listview);
		currenciesList.setAdapter(new CurrenciesAdapter(getApplicationContext(), mViewModel.getCurrencies()));

	}
}
