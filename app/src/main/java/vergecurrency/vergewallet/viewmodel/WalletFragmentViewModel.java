package vergecurrency.vergewallet.viewmodel;

import androidx.lifecycle.ViewModel;
import vergecurrency.vergewallet.service.model.Currency;
import vergecurrency.vergewallet.service.model.PreferencesManager;
import vergecurrency.vergewallet.service.repository.PriceStatsDataReader;
import vergecurrency.vergewallet.wallet.WalletManager;

public class WalletFragmentViewModel extends ViewModel {

    private WalletManager wm;
    private Long balance;
    private String currencyChange;

    public WalletFragmentViewModel() {
        wm = WalletManager.getInstance();
        balance = wm.getBalance().getValue();
        currencyChange = PriceStatsDataReader.readPriceStatistics(getCurrencyCode()).get("XVG/USD");
    }

    public String getCurrencyCode() {
        return Currency.getCurrencyFromJson(PreferencesManager.getInstance().getPreferredCurrency()).getCurrency();
    }


    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
}
