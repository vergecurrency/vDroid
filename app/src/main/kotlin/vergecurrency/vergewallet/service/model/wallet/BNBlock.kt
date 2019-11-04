package vergecurrency.vergewallet.service.model.wallet

import com.google.gson.Gson

class BNBlock {

    var _id: String? = null
    var chain: String? = null
    var network: String? = null
    var hash: String? = null
    var height: Long = 0
    var version: Long = 0
    var size: Long = 0
    var merkleRoot: String? = null
    var time: String? = null
    var timeNormalized: String? = null
    var nonce: Long = 0
    var bits: Long = 0
    var previousBlockHash: String? = null
    var nextBlockHash: String? = null
    var reward: Long = 0
    var transactionCount: Long = 0
    var confirmations: Long = 0

    companion object {


        fun decode(JSON: String): BNBlock {
            return Gson().fromJson(JSON, BNBlock::class.java)
        }
    }
}
