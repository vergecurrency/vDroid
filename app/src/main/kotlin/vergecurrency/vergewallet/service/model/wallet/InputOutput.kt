package vergecurrency.vergewallet.service.model.wallet

import com.google.gson.Gson

import vergecurrency.vergewallet.Constants

class InputOutput {

    var amount: Int = 0
    var address: String? = null
    var isMine: Boolean = false

    fun amountValue(): Float {
        return (amount / Constants.SATOSHIS_DIVIDER).toFloat()
    }

    companion object {

        fun decode(JSON: String): InputOutput {
            return Gson().fromJson(JSON, InputOutput::class.java)
        }
    }
}
