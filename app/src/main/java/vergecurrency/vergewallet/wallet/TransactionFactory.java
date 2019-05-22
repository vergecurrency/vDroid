package vergecurrency.vergewallet.wallet;

public class TransactionFactory {

	private float amount = 0f;
	private float fiatAmount = 0f;
	String address = "";
	String memo = "";
	FiatRateTicker ticker;

	public TransactionFactory(FiatRateTicker ticker) {
		this.ticker = ticker;
	}

	private void setBy(String currency, float amount) {
		if(currency == "XVG") {
			this.amount = amount;
		} else {
			fiatAmount = amount;
		}

		update(currency);
	}

	private void update(String currency) {
		String info = ticker.getRateInfo();
		if (currency == "XVG") {
			fiatAmount = (float)(amount  * info.getPrice());
		} else {
			amount = (float)(fiatAmount / info.getPrice());
		}

	}

}
