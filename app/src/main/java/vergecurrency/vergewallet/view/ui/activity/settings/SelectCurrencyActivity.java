package vergecurrency.vergewallet.view.ui.activity.settings;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ListView;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.utilities.CurrenciesUtils;
import vergecurrency.vergewallet.view.adapter.CurrenciesAdapter;

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
