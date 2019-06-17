package vergecurrency.vergewallet.viewmodel;


import java.util.ArrayList;
import java.util.Map;

import androidx.lifecycle.ViewModel;
import vergecurrency.vergewallet.service.model.Currency;
import vergecurrency.vergewallet.service.model.PreferencesManager;
import vergecurrency.vergewallet.service.repository.PriceStatsDataReader;

import static vergecurrency.vergewallet.wallet.VergeWalletApplication.PREFERENCES_MANAGER;

public class StatisticsViewModel extends ViewModel {

	private String currencyCode;
	private ArrayList<Map.Entry<String, String>> statistics ;

	public StatisticsViewModel() {
		currencyCode = Currency.getCurrencyFromJson(PREFERENCES_MANAGER.getPreferredCurrency()).getCurrency();
		statistics = new ArrayList<>(PriceStatsDataReader.readPriceStatistics(getCurrencyCode()).entrySet());
	}

	private String getCurrencyCode() {
		return currencyCode;
	}

	public ArrayList<Map.Entry<String, String>> getStatistics() {

		return statistics;
	}


}
