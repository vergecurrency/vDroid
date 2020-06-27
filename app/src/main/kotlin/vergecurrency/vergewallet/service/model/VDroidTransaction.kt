package vergecurrency.vergewallet.service.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField


open class VDroidTransaction(@PrimaryKey var id: Int? = 0,
                             @RealmField(name = "tx_id") var address: String? = null,
                             @RealmField(name = "action") var action: String? = null,
                             @RealmField(name = "amount") var amount: Int? = 0,
                             @RealmField(name = "fees") var fees: Int? = 0,
                             @RealmField(name = "time") var time: Int? = 0,
                             @RealmField(name = "confirmations") var confirmations: Int? = 0,
                             @RealmField(name = "block_height") var blockheight: Int? = 0,
                             @RealmField(name = "fee_per_kb") var feePerKb: Int? = 0,
                             @RealmField(name = "saved_address") var savedAddress: String? = null,
                             @RealmField(name = "created_on") var createdOn: Int? = 0,
                             @RealmField(name = "message") var message: String? = null,
                             @RealmField(name = "address_to") var addressTo: String? = null

) : RealmObject() {

}
