package vergecurrency.vergewallet.views.activities.settings;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.helpers.CurrenciesUtils;
import vergecurrency.vergewallet.models.adapters.CurrenciesAdapter;

public class SelectCurrencyActivity extends AppCompatActivity {


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_currency);

		context = getApplicationContext();

		currenciesList = findViewById(R.id.activity_select_currency_listview);
		currenciesList.setAdapter(new CurrenciesAdapter(context,CurrenciesUtils.loadCurrenciesFromFile(context)));

	}


	private ListView currenciesList;
	private Context context;
}
