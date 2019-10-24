package vergecurrency.vergewallet.service.model.wallet;

import java.util.Date;

import vergecurrency.vergewallet.Constants;

public class TxHistory {

	private String txid;
	private String action;
	private long amount;
	private long fees;
	private long time;
	private long confirmations;
	private long blockheight;
	private long feePerKb;
	private InputOutput[] inputs;
	private InputOutput[] outputs;
	private String savedAddress;
	private long createdOn;
	private String message;
	private String addressTo;

	public String getTxid() {
		return txid;
	}

	public void setTxid(String txid) {
		this.txid = txid;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public long getFees() {
		return fees;
	}

	public void setFees(long fees) {
		this.fees = fees;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public long getConfirmations() {
		return confirmations;
	}

	public void setConfirmations(long confirmations) {
		this.confirmations = confirmations;
	}

	public long getBlockheight() {
		return blockheight;
	}

	public void setBlockheight(long blockheight) {
		this.blockheight = blockheight;
	}

	public long getFeePerKb() {
		return feePerKb;
	}

	public void setFeePerKb(long feePerKb) {
		this.feePerKb = feePerKb;
	}

	public InputOutput[] getInputs() {
		return inputs;
	}

	public void setInputs(InputOutput[] inputs) {
		this.inputs = inputs;
	}

	public InputOutput[] getOutputs() {
		return outputs;
	}

	public void setOutputs(InputOutput[] outputs) {
		this.outputs = outputs;
	}

	public String getSavedAddress() {
		return savedAddress;
	}

	public void setSavedAddress(String savedAddress) {
		this.savedAddress = savedAddress;
	}

	public long getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(long createdOn) {
		this.createdOn = createdOn;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAddressTo() {
		return addressTo;
	}

	public void setAddressTo(String addressTo) {
		this.addressTo = addressTo;
	}


	//Some extension methods

	public float amountValue() {
		return (float) (amount / Constants.SATOSHIS_DIVIDER);
	}

	public TxAction getCategory() {
		switch (action) {
			case "received":
				return TxAction.Received;
			case "sent":
				return TxAction.Sent;
			default:
				return TxAction.Moved;
		}
	}


	public String address() {
		if (savedAddress != null) {
			return savedAddress;
		}

		if (getCategory() == TxAction.Received) {
			return inputs[0].getAddress();
		}

		if (getCategory() == TxAction.Moved) {
			return "";
		}

		//ask swift devs wtf they meant
		/*return outputs[0].getAmount() {

		}*/
		return "";
	}

	public Date getTimeReceived() {
		return new Date(time);
	}

	public Date getTimeCreatedOn() {
		return new Date(createdOn);
	}

	public String getMemo() {
		return message;
	}

	public String getConfirmationsCount() {
		return confirmations > 6 ? "6+" : (String) (confirmations < 0 ? "0" : confirmations);
	}

	public boolean isConfirmed() {
		return confirmations >= Constants.NEEDED_CONFIRMATIONS;
	}

	public boolean sortBy(TxHistory txHistory) {
		if (time == txHistory.time) {
			return (double) (txid.toCharArray()[0]) > (double) (txHistory.txid.toCharArray()[0]);
		}
		return time > txHistory.time;

	}
}
