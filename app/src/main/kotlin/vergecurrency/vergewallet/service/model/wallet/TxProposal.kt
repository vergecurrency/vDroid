package vergecurrency.vergewallet.service.model.wallet

import com.google.gson.Gson

class TxProposal {

    var address: String? = null
    var amount: Double = 0.toDouble()
    var message: String? = null

    companion object {

        fun decode(JSON: String): TxProposal {
            return Gson().fromJson(JSON, TxProposal::class.java)
        }
    }
}
