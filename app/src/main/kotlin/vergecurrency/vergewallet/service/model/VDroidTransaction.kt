package vergecurrency.vergewallet.service.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField


data class VDroidTransaction(@PrimaryKey val id: Int,
                             @RealmField(name = "tx_id") val address: String,
                             @RealmField(name = "action") val action: String,
                             @RealmField(name = "amount") val amount: Int,
                             @RealmField(name = "fees") val fees: Int,
                             @RealmField(name = "time") val time: Int,
                             @RealmField(name = "confirmations") val confirmations: Int,
                             @RealmField(name = "block_height") val blockheight: Int,
                             @RealmField(name = "fee_per_kb") val feePerKb: Int,
                             @RealmField(name = "saved_address") val savedAddress: String,
                             @RealmField(name = "created_on") val createdOn: Int,
                             @RealmField(name = "message") val message: String,
                             @RealmField(name = "address_to") val addressTo: String

) : RealmObject() {

}
