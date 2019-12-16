package vergecurrency.vergewallet.wallet

import vergecurrency.vergewallet.service.model.FiatRate

class WalletTransactionFactory(private val ticker: FiatRateTicker) {

    private var amount = 0f
    private var fiatAmount = 0f
    private val address = ""
    private val memo = ""

    private fun setBy(currency: String, amount: Float) {
        if (isXVG(currency)) {
            this.amount = amount
        } else {
            fiatAmount = amount
        }

        update(currency)
    }

    private fun update(currency: String) {
        val info = ticker.rateInfo
        if (isXVG(currency)) {
            fiatAmount = (amount * info!!.price).toFloat()
        } else {
            amount = (fiatAmount / info!!.price).toFloat()
        }

    }

    private fun isXVG(currency: String): Boolean {
        return currency == "XVG"
    }

}
