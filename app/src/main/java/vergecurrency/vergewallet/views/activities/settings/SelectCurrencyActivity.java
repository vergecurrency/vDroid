package vergecurrency.vergewallet.views.activities.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import vergecurrency.vergewallet.R;

public class SelectCurrencyActivity extends AppCompatActivity {


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_currency);

		currenciesList = findViewById(R.id.activity_select_currency_listview);


	}


	private ListView currenciesList;
}
