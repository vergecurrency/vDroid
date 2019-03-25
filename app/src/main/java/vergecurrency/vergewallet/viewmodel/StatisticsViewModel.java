package vergecurrency.vergewallet.viewmodel;


import java.util.ArrayList;
import java.util.Map;

import androidx.lifecycle.ViewModel;
import vergecurrency.vergewallet.service.model.Currency;
import vergecurrency.vergewallet.service.model.PreferencesManager;
import vergecurrency.vergewallet.service.repository.PriceStatsDataReader;

public class StatisticsViewModel extends ViewModel {


	private PreferencesManager pm;
	private String currencyCode;
	private ArrayList<Map.Entry<String, String>> statistics ;

	public StatisticsViewModel() {
		pm = PreferencesManager.getInstance();
		currencyCode = Currency.getCurrencyFromJson(pm.getPreferredCurrency()).getCurrency();
		statistics = new ArrayList<>(PriceStatsDataReader.readPriceStatistics(getCurrencyCode()).entrySet());
	}

	private String getCurrencyCode() {
		return currencyCode;
	}

	public ArrayList<Map.Entry<String, String>> getStatistics() {

		return statistics;
	}


}
