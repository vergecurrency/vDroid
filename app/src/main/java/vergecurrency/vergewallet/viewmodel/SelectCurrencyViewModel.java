package vergecurrency.vergewallet.viewmodel;


import android.app.Application;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import vergecurrency.vergewallet.service.model.Currency;
import vergecurrency.vergewallet.utilities.CurrenciesUtils;


public class SelectCurrencyViewModel extends AndroidViewModel {

	private ArrayList<Currency> currencies;

	public SelectCurrencyViewModel(@NonNull Application application) {
		super(application);

		currencies = CurrenciesUtils.loadCurrenciesFromFile(getApplication().getApplicationContext());
	}

	public ArrayList<Currency> getCurrencies() {
		return currencies;
	}
}
