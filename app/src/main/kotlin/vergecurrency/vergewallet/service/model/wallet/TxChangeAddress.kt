package vergecurrency.vergewallet.service.model.wallet

import com.google.gson.Gson

class TxChangeAddress {

    var isChange: Boolean = false
    var coin: String? = null
    var publicKeys: Array<String>? = null
    var type: String? = null
    var version: String? = null
    var path: String? = null
    var walletId: String? = null
    var createdOn: Long = 0
    var network: String? = null
    var address: String? = null
    var _id: String? = null

    companion object {

        fun decode(JSON: String): TxChangeAddress {
            return Gson().fromJson(JSON, TxChangeAddress::class.java)
        }
    }
}
