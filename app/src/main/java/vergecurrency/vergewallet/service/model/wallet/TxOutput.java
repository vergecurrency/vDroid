package vergecurrency.vergewallet.service.model.wallet;

import androidx.annotation.Nullable;

import com.google.gson.Gson;

import vergecurrency.vergewallet.Constants;

public class TxOutput {

	private long amount;
	@Nullable
	private String message;
	@Nullable
	private String encryptedMessage;
	private String toAddress;
	@Nullable
	private String ephemeralPrivKey;
	@Nullable
	private boolean stealth;

	public static TxOutput decode(String JSON) {
		return new Gson().fromJson(JSON, TxOutput.class);
	}


	public double amountValue() {
		return (amount / Constants.SATOSHIS_DIVIDER);
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	@Nullable
	public String getMessage() {
		return message;
	}

	public void setMessage(@Nullable String message) {
		this.message = message;
	}

	@Nullable
	public String getEncryptedMessage() {
		return encryptedMessage;
	}

	public void setEncryptedMessage(@Nullable String encryptedMessage) {
		this.encryptedMessage = encryptedMessage;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	@Nullable
	public String getEphemeralPrivKey() {
		return ephemeralPrivKey;
	}

	public void setEphemeralPrivKey(@Nullable String ephemeralPrivKey) {
		this.ephemeralPrivKey = ephemeralPrivKey;
	}

	public boolean isStealth() {
		return stealth;
	}

	public void setStealth(boolean stealth) {
		this.stealth = stealth;
	}
}
