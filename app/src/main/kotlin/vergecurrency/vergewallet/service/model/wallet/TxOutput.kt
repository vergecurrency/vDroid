package vergecurrency.vergewallet.service.model.wallet

import com.google.gson.Gson

import vergecurrency.vergewallet.Constants

class TxOutput {

    var amount: Long = 0
    var message: String? = null
    var encryptedMessage: String? = null
    var toAddress: String? = null
    var ephemeralPrivKey: String? = null
    var isStealth: Boolean = false


    fun amountValue(): Double {
        return amount / Constants.SATOSHIS_DIVIDER
    }

    companion object {

        fun decode(JSON: String): TxOutput {
            return Gson().fromJson(JSON, TxOutput::class.java)
        }
    }
}
