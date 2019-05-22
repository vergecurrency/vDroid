package vergecurrency.vergewallet.service.model.vws;

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

		if(getCategory() == TxAction.Moved) {
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
		return confirmations > 6 ? "6+" : (String)(confirmations < 0 ? 0 +"" : confirmations);
	}

	public boolean isConfirmed() {
		return confirmations >= Constants.NEEDED_CONFIRMATIONS;
	}


}
