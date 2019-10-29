package vergecurrency.vergewallet.service.model.wallet;

import com.google.gson.Gson;

import vergecurrency.vergewallet.Constants;

public class WalletBalanceInfo {

	private double totalAmount;
	private double lockedAmount;
	private double totalConfirmedAmount;
	private double lockedConfirmedAmount;
	private double availableAmount;
	private double availableConfirmedAmount;
	private AddressBalance[] byAddress;

	public static WalletBalanceInfo decode(String JSON) {
		return new Gson().fromJson(JSON, WalletBalanceInfo.class);
	}

	public double totalAmountValue() {
		return (totalAmount / Constants.SATOSHIS_DIVIDER);
	}
	public double lockedAmountValue() {
		return (lockedAmount / Constants.SATOSHIS_DIVIDER);
	}
	public double totalConfirmedAmountValue() {
		return (totalConfirmedAmount / Constants.SATOSHIS_DIVIDER);
	}
	public double lockedConfirmedAmountValue() {
		return (lockedConfirmedAmount / Constants.SATOSHIS_DIVIDER);
	}
	public double availableAmountValue() {
		return (availableAmount / Constants.SATOSHIS_DIVIDER);
	}
	public double availableConfirmedAmountValue() {
		return (availableConfirmedAmount/ Constants.SATOSHIS_DIVIDER);
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getLockedAmount() {
		return lockedAmount;
	}

	public void setLockedAmount(double lockedAmount) {
		this.lockedAmount = lockedAmount;
	}

	public double getTotalConfirmedAmount() {
		return totalConfirmedAmount;
	}

	public void setTotalConfirmedAmount(double totalConfirmedAmount) {
		this.totalConfirmedAmount = totalConfirmedAmount;
	}

	public double getLockedConfirmedAmount() {
		return lockedConfirmedAmount;
	}

	public void setLockedConfirmedAmount(double lockedConfirmedAmount) {
		this.lockedConfirmedAmount = lockedConfirmedAmount;
	}

	public double getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(double availableAmount) {
		this.availableAmount = availableAmount;
	}

	public double getAvailableConfirmedAmount() {
		return availableConfirmedAmount;
	}

	public void setAvailableConfirmedAmount(double availableConfirmedAmount) {
		this.availableConfirmedAmount = availableConfirmedAmount;
	}

	public AddressBalance[] getByAddress() {
		return byAddress;
	}

	public void setByAddress(AddressBalance[] byAddress) {
		this.byAddress = byAddress;
	}
}
