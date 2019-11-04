package vergecurrency.vergewallet.service.model.wallet

import java.util.Date

class AddressInfo {

    var network: String? = null
    var path: String? = null
    var isChange: Boolean = false
    var coin: String? = null
    var _id: String? = null
    var type: String? = null
    var createdOn: Int = 0
    var version: String? = null
    var publicKeys: Array<String>? = null
    var address: String? = null
    var walletId: String? = null
    var isHasActivity: Boolean = false

    fun createdOnDate(): Date {
        return Date(createdOn.toLong())
    }


}
