package vergecurrency.vergewallet.service.model.vws;

import vergecurrency.vergewallet.Constants;

public class InputOutput {

	private int amount;
	private String address;
	private boolean isMine;

	float amountValue() {
		return (float) (amount / Constants.SATOSHIS_DIVIDER);
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isMine() {
		return isMine;
	}

	public void setMine(boolean mine) {
		isMine = mine;
	}
}
