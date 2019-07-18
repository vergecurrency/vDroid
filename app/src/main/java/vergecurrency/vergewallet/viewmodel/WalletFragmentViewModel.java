package vergecurrency.vergewallet.viewmodel;

import androidx.lifecycle.ViewModel;
import vergecurrency.vergewallet.service.model.Currency;
import vergecurrency.vergewallet.service.model.PreferencesManager;
import vergecurrency.vergewallet.service.repository.PriceStatsDataReader;
import vergecurrency.vergewallet.wallet.WalletManager;

public class WalletFragmentViewModel extends ViewModel {
    private Long balance;
    //TODO check if necessary
    //private String currencyChange;

    public WalletFragmentViewModel() {
        balance = WalletManager.getBalance().getValue();
        //TODO check if necessary
        //currencyChange = PriceStatsDataReader.readPriceStatistics(getCurrencyCode()).get("XVG/USD");
    }

    public String getCurrencyCode() {
        return Currency.getCurrencyFromJson(PreferencesManager.getPreferredCurrency()).getCurrency();
    }


    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
}