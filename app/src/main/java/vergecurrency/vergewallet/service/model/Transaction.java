package vergecurrency.vergewallet.service.model;

public class Transaction {
	
	private String txid;
	private String vout;
	private String account;
	private String address;
	private String category;
	private double amount;
	private int confirmations;
	private String blockhash;
	private int blockindex;
	private long blocktime;
	private long time;
	private long timereceived;
	
	public String getTxid() {
		return txid;
	}
	
	public void setTxid(String txid) {
		this.txid = txid;
	}
	
	public String getVout() {
		return vout;
	}
	
	public void setVout(String vout) {
		this.vout = vout;
	}
	
	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public int getConfirmations() {
		return confirmations;
	}
	
	public void setConfirmations(int confirmations) {
		this.confirmations = confirmations;
	}
	
	public String getBlockhash() {
		return blockhash;
	}
	
	public void setBlockhash(String blockhash) {
		this.blockhash = blockhash;
	}
	
	public int getBlockindex() {
		return blockindex;
	}
	
	public void setBlockindex(int blockindex) {
		this.blockindex = blockindex;
	}
	
	public long getBlocktime() {
		return blocktime;
	}
	
	public void setBlocktime(long blocktime) {
		this.blocktime = blocktime;
	}
	
	public long getTime() {
		return time;
	}
	
	public void setTime(long time) {
		this.time = time;
	}
	
	public long getTimereceived() {
		return timereceived;
	}
	
	public void setTimereceived(long timereceived) {
		this.timereceived = timereceived;
	}
}
