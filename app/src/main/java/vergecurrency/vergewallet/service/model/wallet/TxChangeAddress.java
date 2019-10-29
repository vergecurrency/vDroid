package vergecurrency.vergewallet.service.model.wallet;

import com.google.gson.Gson;

public class TxChangeAddress {

	private boolean isChange;
	private String coin;
	private String[] publicKeys;
	private String type;
	private String version;
	private String path;
	private String walletId;
	private long createdOn;
	private String network;
	private String address;
	private String _id;

	public static TxChangeAddress decode(String JSON) {
		return new Gson().fromJson(JSON, TxChangeAddress.class);
	}

	public boolean isChange() {
		return isChange;
	}

	public void setChange(boolean change) {
		isChange = change;
	}

	public String getCoin() {
		return coin;
	}

	public void setCoin(String coin) {
		this.coin = coin;
	}

	public String[] getPublicKeys() {
		return publicKeys;
	}

	public void setPublicKeys(String[] publicKeys) {
		this.publicKeys = publicKeys;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	public long getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(long createdOn) {
		this.createdOn = createdOn;
	}

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}
}
