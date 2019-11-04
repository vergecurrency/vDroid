package vergecurrency.vergewallet.service.model.wallet

import vergecurrency.vergewallet.Constants

class AddressBalance {
    var address: String? = null
    var amount: Double = 0.toDouble()

    var path: String? = null


    val amountValue: Double
        get() = amount / Constants.SATOSHIS_DIVIDER
}
