package vergecurrency.vergewallet.views.activities.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.helpers.CurrenciesUtils;
import vergecurrency.vergewallet.models.adapters.CurrenciesAdapter;
import vergecurrency.vergewallet.models.dataproc.PreferencesManager;
import vergecurrency.vergewallet.structs.Currency;
import vergecurrency.vergewallet.views.activities.MainActivity;
import vergecurrency.vergewallet.views.activities.setup.SetupWalletActivity;

import static android.support.v4.content.ContextCompat.startActivity;


public class SelectCurrencyActivity extends AppCompatActivity {
	private String currencyCode;
	private PreferencesManager pm;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		pm = new PreferencesManager(this);
		currencyCode  = Currency.getCurrencyFromJson(pm.getSelectedCurrency()).getCurrency();

		setContentView(R.layout.activity_select_currency);

		context = getApplicationContext();
		currenciesList = findViewById(R.id.activity_select_currency_listview);
		currenciesList.setAdapter(new CurrenciesAdapter(context,CurrenciesUtils.loadCurrenciesFromFile(context)));
        TextView selectedFiat = findViewById(R.id.selected_Fiat);
		if (currencyCode !=null) {
			selectedFiat.setText(String.format( currencyCode));
		} else {
			selectedFiat.setText("USD");
		}


    }



    private ListView currenciesList;
	private Context context;


}
