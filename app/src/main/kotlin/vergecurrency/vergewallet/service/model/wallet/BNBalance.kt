package vergecurrency.vergewallet.service.model.wallet

import com.google.gson.Gson

class BNBalance {

    var confirmed: Long = 0
    var unconfirmed: Long = 0
    var balance: Long = 0

    companion object {

        fun decode(JSON: String): BNBalance {
            return Gson().fromJson(JSON, BNBalance::class.java)
        }
    }
}
