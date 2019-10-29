package vergecurrency.vergewallet.service.model.wallet;

import com.google.gson.Gson;

public class TxProposal {

	private String address;
	private double amount;
	private String message;

	public static TxProposal decode(String JSON) {
		return new Gson().fromJson(JSON, TxProposal.class);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
