package vergecurrency.vergewallet.service.model

import java.io.Serializable
import java.util.Comparator
import java.util.Date

class Transaction : Comparable<Transaction>, Serializable {

    var txid: String? = null
    var vout: String? = null
    var account: String? = null
    var address: String? = null
    var category: String? = null
    var amount: Double = 0.toDouble()
    var confirmations: Int = 0
    var blockhash: String? = null
    var blockindex: Int = 0
    var blocktime: Long = 0
    var time: Long = 0
    var timereceived: Long = 0

     val isSend: Boolean
        get() = category == "send"

    val isReceive: Boolean
        get() = category == "receive"

    override fun compareTo(other: Transaction): Int {
        return 0
    }

    companion object {

        val TimeComparatorDESC : (Transaction, Transaction) -> Int = { tx1: Transaction, tx2: Transaction ->
            val tx1value = Date(tx1.time * 1000)
            val tx2value = Date(tx2.time * 1000)
            tx2value.compareTo(tx1value)
        }
    }
}
