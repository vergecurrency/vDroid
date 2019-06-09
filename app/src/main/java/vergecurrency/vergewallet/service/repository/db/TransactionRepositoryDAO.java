package vergecurrency.vergewallet.service.repository.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

@Entity(nameInDb = "transaction")
public class TransactionRepositoryDAO {

    @Id(autoincrement = true)
    private long id;
    @Property(nameInDb = "txid")
    private String txid ;
    @Property(nameInDb = "action")
    private String action ;
    @Property(nameInDb = "amount")
    private long amount;
    @Property(nameInDb = "fees")
    private long fees;
    @Property(nameInDb = "time")
    private long time;
    @Property(nameInDb = "confirmations")
    private long confirmations;
    @Property(nameInDb = "blockheight")
    private long blockheight ;
    @Property(nameInDb = "fee_per_kb")
    private long feePerKb ;
    @Property(nameInDb = "saved_address")
    private String savedAddress ;
    @Property(nameInDb = "created_on")
    private long createdOn;
    @Property(nameInDb = "message")
    private String message ;
    @Property(nameInDb = "address_to")
    private String addressTo;
}
