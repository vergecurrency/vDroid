package vergecurrency.vergewallet.service.model

import com.google.gson.Gson

class Currency(name: String, currency: String) {

    var currency: String? = currency
    var name: String? = name

    val currencyAsJSON: String
        get() = Gson().toJson(this)


    companion object {

        fun getCurrencyFromJson(json: String): Currency {
            return Gson().fromJson(json, Currency::class.java)
        }
    }
}
