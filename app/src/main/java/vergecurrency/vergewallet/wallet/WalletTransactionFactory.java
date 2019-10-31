package vergecurrency.vergewallet.wallet;

import vergecurrency.vergewallet.service.model.FiatRate;

public class WalletTransactionFactory {

    private float amount = 0f;
    private float fiatAmount = 0f;
    private String address = "";
    private String memo = "";
    private FiatRateTicker ticker;

    public WalletTransactionFactory(FiatRateTicker ticker) {
        this.ticker = ticker;
    }

    private void setBy(String currency, float amount) {
        if (isXVG(currency)) {
            this.amount = amount;
        } else {
            fiatAmount = amount;
        }

        update(currency);
    }

    private void update(String currency) {
        FiatRate info = ticker.getRateInfo();
        if (isXVG(currency)) {
             fiatAmount = (float) (amount * info.getPrice());
        } else {
            amount = (float) (fiatAmount / info.getPrice());
        }

    }

    private boolean isXVG(String currency) {
        return currency.equals("XVG");
    }

}
