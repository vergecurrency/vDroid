package vergecurrency.vergewallet.service.model.vws;

import androidx.annotation.Nullable;

import java.util.Date;

public class AddressInfo {

	private String network;
	private String path;
	private boolean isChange;
	private String coin;
	@Nullable
	private String _id;
	private String type;
	private int createdOn;
	private String version;
	private String[] publicKeys;
	private String address;
	private String walletId;
	@Nullable
	private boolean hasActivity;

	public AddressInfo() {

	}

	public Date createdOnDate() {
		return new Date(createdOn);
	}


	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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

	@Nullable
	public String get_id() {
		return _id;
	}

	public void set_id(@Nullable String _id) {
		this._id = _id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(int createdOn) {
		this.createdOn = createdOn;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String[] getPublicKeys() {
		return publicKeys;
	}

	public void setPublicKeys(String[] publicKeys) {
		this.publicKeys = publicKeys;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	public boolean isHasActivity() {
		return hasActivity;
	}

	public void setHasActivity(boolean hasActivity) {
		this.hasActivity = hasActivity;
	}


}
