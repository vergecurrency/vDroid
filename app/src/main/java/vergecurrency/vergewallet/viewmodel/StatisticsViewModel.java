package vergecurrency.vergewallet.viewmodel;


import java.util.ArrayList;
import java.util.Map;

import androidx.lifecycle.ViewModel;
import vergecurrency.vergewallet.service.model.Currency;
import vergecurrency.vergewallet.service.repository.RatesClient;

import static vergecurrency.vergewallet.service.model.PreferencesManager.getPreferredCurrency;

public class StatisticsViewModel extends ViewModel {

	private String currencyCode;
	private ArrayList<Map.Entry<String, String>> statistics ;

	public StatisticsViewModel() {
		currencyCode = Currency.getCurrencyFromJson(getPreferredCurrency()).getCurrency();
		statistics = new ArrayList<>(RatesClient.readPriceStatistics(getCurrencyCode()).entrySet());
	}

	private String getCurrencyCode() {
		return currencyCode;
	}

	public ArrayList<Map.Entry<String, String>> getStatistics() {

		return statistics;
	}


}
