package vergecurrency.vergewallet.service.model;

import java.util.Comparator;
import java.util.Date;

public class Transaction implements Comparable<Transaction> {

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

    public boolean isSend(){
        return  getCategory().equals("send");
    }

    public boolean isReceive(){
        return getCategory().equals("receive");
    }

    @Override
    public int compareTo(Transaction o) {
        return 0;
    }

    public static Comparator<Transaction> TimeComparatorASC
            = (Transaction tx1, Transaction tx2) -> {
        Long tx1value = tx1.getTime();
        Long tx2value = tx2.getTime();
        return tx1value.compareTo(tx2value);
    };

    public static Comparator<Transaction> TimeComparatorDESC
            = (Transaction tx1, Transaction tx2) -> {
        Date tx1value = new Date(tx1.getTime() * 1000);
        Date tx2value = new Date(tx2.getTime() * 1000);
        return tx2value.compareTo(tx1value);
    };

    public static Comparator<Transaction> AmountComparatorASC
            = (Transaction tx1, Transaction tx2) -> {
        Double tx1value = tx1.getAmount();
        Double tx2value = tx2.getAmount();
        return tx1value.compareTo(tx2value);
    };

    public static Comparator<Transaction> AmountComparatorDESC
            = (Transaction tx1, Transaction tx2) -> {
        Double tx1value = tx1.getAmount();
        Double tx2value = tx2.getAmount();
        return tx2value.compareTo(tx1value);
    };

    public static Comparator<Transaction> AddressComparatorASC
            = (Transaction tx1, Transaction tx2) -> {
        String tx1value = tx1.getAddress();
        String tx2value = tx2.getAddress();
        return tx1value.compareTo(tx2value);
    };

    public static Comparator<Transaction> AddressComparatorDESC
            = (Transaction tx1, Transaction tx2) -> {
        String tx1value = tx1.getAddress();
        String tx2value = tx2.getAddress();
        return tx2value.compareTo(tx1value);
    };
}
