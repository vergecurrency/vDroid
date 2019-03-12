package vergecurrency.vergewallet.viewmodel;


import java.util.ArrayList;
import java.util.Map;

import androidx.lifecycle.ViewModel;
import vergecurrency.vergewallet.service.model.Currency;
import vergecurrency.vergewallet.service.model.PreferencesManager;
import vergecurrency.vergewallet.service.repository.PriceStatsDataReader;

public class StatisticsViewModel extends ViewModel {


	private PreferencesManager pm;

	public StatisticsViewModel() {
		pm = PreferencesManager.getInstance();
	}

	private String getCurrencyCode() {
		return Currency.getCurrencyFromJson(pm.getPreferredCurrency()).getCurrency();
	}

	public ArrayList<Map.Entry<String, String>> getStatistics() {

		return new ArrayList<>(PriceStatsDataReader.readPriceStatistics(getCurrencyCode()).entrySet());
	}


}
