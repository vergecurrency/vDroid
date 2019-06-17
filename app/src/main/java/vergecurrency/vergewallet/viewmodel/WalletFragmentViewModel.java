package vergecurrency.vergewallet.viewmodel;

import androidx.lifecycle.ViewModel;

import vergecurrency.vergewallet.service.model.Currency;
import vergecurrency.vergewallet.service.repository.PriceStatsDataReader;
import vergecurrency.vergewallet.wallet.WalletManager;

import static vergecurrency.vergewallet.wallet.VergeWalletApplication.PREFERENCES_MANAGER;

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
        return Currency.getCurrencyFromJson(PREFERENCES_MANAGER.getPreferredCurrency()).getCurrency();
    }


    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
}
