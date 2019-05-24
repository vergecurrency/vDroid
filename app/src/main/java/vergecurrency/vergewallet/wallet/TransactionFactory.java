package vergecurrency.vergewallet.wallet;

public class TransactionFactory {

    private float amount = 0f;
    private float fiatAmount = 0f;
    private String address = "";
    private String memo = "";
    private FiatRateTicker ticker;

    public TransactionFactory(FiatRateTicker ticker) {
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
        String info = ticker.getRateInfo();
        if (isXVG(currency)) {
            //TODO Uncomment
            // fiatAmount = (float) (amount * info.getPrice());
        } else {
            //TODO Uncomment
            // amount = (float) (fiatAmount / info.getPrice());
        }

    }

    private boolean isXVG(String currency) {
        return currency.equals("XVG");
    }

}
