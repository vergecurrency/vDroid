package vergecurrency.vergewallet.service.model.wallet;

import com.google.gson.Gson;

public class BNBalance {

	private long confirmed;
	private long unconfirmed;
	private long balance;

	public static BNBalance decode(String JSON) {
		return new Gson().fromJson(JSON, BNBalance.class);
	}

	public long getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(long confirmed) {
		this.confirmed = confirmed;
	}

	public long getUnconfirmed() {
		return unconfirmed;
	}

	public void setUnconfirmed(long unconfirmed) {
		this.unconfirmed = unconfirmed;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}
}
