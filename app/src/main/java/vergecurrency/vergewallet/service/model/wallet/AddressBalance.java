package vergecurrency.vergewallet.service.model.wallet;

import androidx.annotation.Nullable;

import vergecurrency.vergewallet.Constants;

public class AddressBalance {
	private String address;
	private double amount;

	@Nullable
	private String path;


	public double getAmountValue() {
		return (amount / Constants.SATOSHIS_DIVIDER);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Nullable
	public String getPath() {
		return path;
	}

	public void setPath(@Nullable String path) {
		this.path = path;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}
