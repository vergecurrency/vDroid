package vergecurrency.vergewallet.service.model.wallet;

import com.google.gson.Gson;

public class BNBlock {

	private String _id;
	private String chain;
	private String network;
	private String hash;
	private long height;
	private long version;
	private long size;
	private String merkleRoot;
	private String time;
	private String timeNormalized;
	private long nonce;
	private long bits;
	private String previousBlockHash;
	private String nextBlockHash;
	private long reward;
	private long transactionCount;
	private long confirmations;


	public static BNBlock decode(String JSON) {
		return new Gson().fromJson(JSON,BNBlock.class);
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getChain() {
		return chain;
	}

	public void setChain(String chain) {
		this.chain = chain;
	}

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public long getHeight() {
		return height;
	}

	public void setHeight(long height) {
		this.height = height;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getMerkleRoot() {
		return merkleRoot;
	}

	public void setMerkleRoot(String merkleRoot) {
		this.merkleRoot = merkleRoot;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTimeNormalized() {
		return timeNormalized;
	}

	public void setTimeNormalized(String timeNormalized) {
		this.timeNormalized = timeNormalized;
	}

	public long getNonce() {
		return nonce;
	}

	public void setNonce(long nonce) {
		this.nonce = nonce;
	}

	public long getBits() {
		return bits;
	}

	public void setBits(long bits) {
		this.bits = bits;
	}

	public String getPreviousBlockHash() {
		return previousBlockHash;
	}

	public void setPreviousBlockHash(String previousBlockHash) {
		this.previousBlockHash = previousBlockHash;
	}

	public String getNextBlockHash() {
		return nextBlockHash;
	}

	public void setNextBlockHash(String nextBlockHash) {
		this.nextBlockHash = nextBlockHash;
	}

	public long getReward() {
		return reward;
	}

	public void setReward(long reward) {
		this.reward = reward;
	}

	public long getTransactionCount() {
		return transactionCount;
	}

	public void setTransactionCount(long transactionCount) {
		this.transactionCount = transactionCount;
	}

	public long getConfirmations() {
		return confirmations;
	}

	public void setConfirmations(long confirmations) {
		this.confirmations = confirmations;
	}
}
