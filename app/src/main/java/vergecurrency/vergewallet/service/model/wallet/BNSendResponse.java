package vergecurrency.vergewallet.service.model.wallet;

import com.google.gson.Gson;

public class BNSendResponse {

	private String txid;

	public static BNSendResponse decode(String JSON) {
		return new Gson().fromJson(JSON, BNSendResponse.class);
	}

	public String getTxid() {
		return txid;
	}

	public void setTxid(String txid) {
		this.txid = txid;
	}
}
