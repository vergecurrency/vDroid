package vergecurrency.vergewallet.service.model.wallet

import com.google.gson.Gson

import vergecurrency.vergewallet.Constants

class WalletBalanceInfo {

    var totalAmount: Double = 0.toDouble()
    var lockedAmount: Double = 0.toDouble()
    var totalConfirmedAmount: Double = 0.toDouble()
    var lockedConfirmedAmount: Double = 0.toDouble()
    var availableAmount: Double = 0.toDouble()
    var availableConfirmedAmount: Double = 0.toDouble()
    var byAddress: Array<AddressBalance>? = null

    fun totalAmountValue(): Double {
        return totalAmount / Constants.SATOSHIS_DIVIDER
    }

    fun lockedAmountValue(): Double {
        return lockedAmount / Constants.SATOSHIS_DIVIDER
    }

    fun totalConfirmedAmountValue(): Double {
        return totalConfirmedAmount / Constants.SATOSHIS_DIVIDER
    }

    fun lockedConfirmedAmountValue(): Double {
        return lockedConfirmedAmount / Constants.SATOSHIS_DIVIDER
    }

    fun availableAmountValue(): Double {
        return availableAmount / Constants.SATOSHIS_DIVIDER
    }

    fun availableConfirmedAmountValue(): Double {
        return availableConfirmedAmount / Constants.SATOSHIS_DIVIDER
    }

    companion object {

        fun decode(JSON: String): WalletBalanceInfo {
            return Gson().fromJson(JSON, WalletBalanceInfo::class.java)
        }
    }
}
