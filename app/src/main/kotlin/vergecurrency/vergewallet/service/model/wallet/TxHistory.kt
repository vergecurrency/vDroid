package vergecurrency.vergewallet.service.model.wallet

import com.google.gson.Gson

import java.util.Date

import vergecurrency.vergewallet.Constants

class TxHistory {

    var txid: String? = null
    var action: String? = null
    var amount: Long = 0
    var fees: Long = 0
    var time: Long = 0
    var confirmations: Long = 0
    var blockheight: Long = 0
    var feePerKb: Long = 0
    var inputs: Array<InputOutput>? = null
    var outputs: Array<InputOutput>? = null
    var savedAddress: String? = null
    var createdOn: Long = 0
    var message: String? = null
    var addressTo: String? = null

    val category: TxAction
        get() {
            when (action) {
                "received" -> return TxAction.Received
                "sent" -> return TxAction.Sent
                else -> return TxAction.Moved
            }
        }

    val timeReceived: Date
        get() = Date(time)

    val timeCreatedOn: Date
        get() = Date(createdOn)

    val confirmationsCount: String
        get() = if (confirmations > 6) "6+" else (if (confirmations < 0) "0" else confirmations) as String

    val isConfirmed: Boolean
        get() = confirmations >= Constants.NEEDED_CONFIRMATIONS


    //Some extension methods

    fun amountValue(): Float {
        return (amount / Constants.SATOSHIS_DIVIDER).toFloat()
    }


    fun address(): String? {
        if (savedAddress != null) {
            return savedAddress
        }

        if (category === TxAction.Received) {
            return inputs!![0].address
        }

        return if (category === TxAction.Moved) {
            ""
        } else ""

        //ask swift devs wtf they meant
        /*return outputs[0].getAmount() {

		}*/
    }

    fun getMemo(): String? {
        return message
    }

    fun sortBy(txHistory: TxHistory): Boolean {
        return if (time == txHistory.time) {
            txid!!.toCharArray()[0].toDouble() > txHistory.txid!!.toCharArray()[0].toDouble()
        } else time > txHistory.time

    }

    companion object {

        fun decode(JSON: String): TxHistory {
            return Gson().fromJson(JSON, TxHistory::class.java)
        }


        fun decodeArray(JSON: String): Array<TxHistory> {
            return Gson().fromJson(JSON, Array<TxHistory>::class.java)
        }
    }
}
