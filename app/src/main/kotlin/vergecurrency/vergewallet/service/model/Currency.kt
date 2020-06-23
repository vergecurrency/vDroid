package vergecurrency.vergewallet.service.model

import com.google.gson.Gson

class Currency(name: String, currency: CharArray) {

    var currency: CharArray? = currency
    var name: String? = name

    val currencyAsJSON: String
        get() = Gson().toJson(this)


    companion object {

        fun getCurrencyFromJson(json: CharArray): Currency {
            return Gson().fromJson(String(json), Currency::class.java)
        }
    }
}
