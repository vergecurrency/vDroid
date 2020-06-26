package vergecurrency.vergewallet.service.model

import com.google.gson.Gson

class Currency(name: String, currency: ByteArray) {

    var currency: ByteArray? = currency
    var name: String? = name

    val currencyAsJSON: String
        get() = Gson().toJson(this)


    companion object {

        fun getCurrencyFromJson(json: ByteArray): Currency {
            return Gson().fromJson(String(json), Currency::class.java)
        }
    }
}
