package vergecurrency.vergewallet.service.model.wallet

import com.google.gson.Gson
import java.util.*

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


    companion object {

        fun decode(JSON: String): AddressInfo {
            return Gson().fromJson(JSON, AddressInfo::class.java)
        }

        fun decodeArray(JSON: String): Array<AddressInfo> {
            return Gson().fromJson(JSON, Array<AddressInfo>::class.java)
        }
    }


}
