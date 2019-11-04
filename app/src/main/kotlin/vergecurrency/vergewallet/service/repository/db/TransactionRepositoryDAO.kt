package vergecurrency.vergewallet.service.repository.db

import org.greenrobot.greendao.annotation.Entity
import org.greenrobot.greendao.annotation.Id
import org.greenrobot.greendao.annotation.Property

@Entity(nameInDb = "transaction")
class TransactionRepositoryDAO {

    @Id(autoincrement = true)
    private val id: Long = 0
    @Property(nameInDb = "txid")
    private val txid: String? = null
    @Property(nameInDb = "action")
    private val action: String? = null
    @Property(nameInDb = "amount")
    private val amount: Long = 0
    @Property(nameInDb = "fees")
    private val fees: Long = 0
    @Property(nameInDb = "time")
    private val time: Long = 0
    @Property(nameInDb = "confirmations")
    private val confirmations: Long = 0
    @Property(nameInDb = "blockheight")
    private val blockheight: Long = 0
    @Property(nameInDb = "fee_per_kb")
    private val feePerKb: Long = 0
    @Property(nameInDb = "saved_address")
    private val savedAddress: String? = null
    @Property(nameInDb = "created_on")
    private val createdOn: Long = 0
    @Property(nameInDb = "message")
    private val message: String? = null
    @Property(nameInDb = "address_to")
    private val addressTo: String? = null
}
