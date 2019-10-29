package vergecurrency.vergewallet.service.model.wallet;

import com.google.gson.Gson;

public class SendMaxInfo {

	private long size;
	private long amount;
	private long fee;
	private long feePerKb;
	private long utxosBelowFee;
	private long amountBelowFee;
	private long utxosAboveMaxSize;
	private long amountAboveMaxSize;

	public static SendMaxInfo decode(String JSON) {
		return new Gson().fromJson(JSON, SendMaxInfo.class);
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public long getFee() {
		return fee;
	}

	public void setFee(long fee) {
		this.fee = fee;
	}

	public long getFeePerKb() {
		return feePerKb;
	}

	public void setFeePerKb(long feePerKb) {
		this.feePerKb = feePerKb;
	}

	public long getUtxosBelowFee() {
		return utxosBelowFee;
	}

	public void setUtxosBelowFee(long utxosBelowFee) {
		this.utxosBelowFee = utxosBelowFee;
	}

	public long getAmountBelowFee() {
		return amountBelowFee;
	}

	public void setAmountBelowFee(long amountBelowFee) {
		this.amountBelowFee = amountBelowFee;
	}

	public long getUtxosAboveMaxSize() {
		return utxosAboveMaxSize;
	}

	public void setUtxosAboveMaxSize(long utxosAboveMaxSize) {
		this.utxosAboveMaxSize = utxosAboveMaxSize;
	}

	public long getAmountAboveMaxSize() {
		return amountAboveMaxSize;
	}

	public void setAmountAboveMaxSize(long amountAboveMaxSize) {
		this.amountAboveMaxSize = amountAboveMaxSize;
	}
}
