package vergecurrency.vergewallet.service.model.wallet

import com.google.gson.Gson

class SendMaxInfo {

    var size: Long = 0
    var amount: Long = 0
    var fee: Long = 0
    var feePerKb: Long = 0
    var utxosBelowFee: Long = 0
    var amountBelowFee: Long = 0
    var utxosAboveMaxSize: Long = 0
    var amountAboveMaxSize: Long = 0

    companion object {

        fun decode(JSON: String): SendMaxInfo {
            return Gson().fromJson(JSON, SendMaxInfo::class.java)
        }
    }
}
