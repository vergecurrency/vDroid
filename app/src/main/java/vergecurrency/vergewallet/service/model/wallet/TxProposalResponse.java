package vergecurrency.vergewallet.service.model.wallet;

import androidx.annotation.Nullable;

import com.google.gson.Gson;

public class TxProposalResponse {

	@Nullable
	private int createdOn;
	private String coin;
	private String id;
	private String network;
	@Nullable
	private String message;
	private UnspentOutput[] inputs;
	private long fee;
	private String status;
	private String creatorId;
	private long walletN;
	private long walletM;
	private TxOutput[] outputs;
	private long amount;
	private TxChangeAddress changeAddress;
	private String walletId;
	private long requiredSignatures;
	private long version;
	private boolean excludeUnconfirmedUtxos;
	private String addressType;
	private long requiredRejections;
	private long[] outputOrder;
	private String[] inputPaths;

	public static TxProposalResponse decode(String JSON) {
		return new Gson().fromJson(JSON, TxProposalResponse.class);
	}

	public int getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(int createdOn) {
		this.createdOn = createdOn;
	}

	public String getCoin() {
		return coin;
	}

	public void setCoin(String coin) {
		this.coin = coin;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	@Nullable
	public String getMessage() {
		return message;
	}

	public void setMessage(@Nullable String message) {
		this.message = message;
	}

	public UnspentOutput[] getInputs() {
		return inputs;
	}

	public void setInputs(UnspentOutput[] inputs) {
		this.inputs = inputs;
	}

	public long getFee() {
		return fee;
	}

	public void setFee(long fee) {
		this.fee = fee;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public long getWalletN() {
		return walletN;
	}

	public void setWalletN(long walletN) {
		this.walletN = walletN;
	}

	public long getWalletM() {
		return walletM;
	}

	public void setWalletM(long walletM) {
		this.walletM = walletM;
	}

	public TxOutput[] getOutputs() {
		return outputs;
	}

	public void setOutputs(TxOutput[] outputs) {
		this.outputs = outputs;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public TxChangeAddress getChangeAddress() {
		return changeAddress;
	}

	public void setChangeAddress(TxChangeAddress changeAddress) {
		this.changeAddress = changeAddress;
	}

	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	public long getRequiredSignatures() {
		return requiredSignatures;
	}

	public void setRequiredSignatures(long requiredSignatures) {
		this.requiredSignatures = requiredSignatures;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public boolean isExcludeUnconfirmedUtxos() {
		return excludeUnconfirmedUtxos;
	}

	public void setExcludeUnconfirmedUtxos(boolean excludeUnconfirmedUtxos) {
		this.excludeUnconfirmedUtxos = excludeUnconfirmedUtxos;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public long getRequiredRejections() {
		return requiredRejections;
	}

	public void setRequiredRejections(long requiredRejections) {
		this.requiredRejections = requiredRejections;
	}

	public long[] getOutputOrder() {
		return outputOrder;
	}

	public void setOutputOrder(long[] outputOrder) {
		this.outputOrder = outputOrder;
	}

	public String[] getInputPaths() {
		return inputPaths;
	}

	public void setInputPaths(String[] inputPaths) {
		this.inputPaths = inputPaths;
	}
}
