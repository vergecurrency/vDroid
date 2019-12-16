package vergecurrency.vergewallet.service.model

import com.google.gson.Gson

import java.io.Serializable

class FiatRate : Serializable {

    var rank: Int = 0
    var price: Double = 0.toDouble()
    var openday: Double = 0.toDouble()
    var highday: Double = 0.toDouble()
    var lowday: Double = 0.toDouble()
    var open24Hour: Double = 0.toDouble()
    var high24Hour: Double = 0.toDouble()
    var low24Hour: Double = 0.toDouble()
    var change24Hour: Double = 0.toDouble()
    var changepct24Hour: Double = 0.toDouble()
    var changeday: Double = 0.toDouble()
    var changepctday: Double = 0.toDouble()
    var supply: Double = 0.toDouble()
    var mktcap: Double = 0.toDouble()
    var totalvolume24H: Double = 0.toDouble()
    var totalvolume24Hto: Double = 0.toDouble()

    companion object {

        fun decode(JSON: String): FiatRate {
            return Gson().fromJson(JSON, FiatRate::class.java)
        }
    }
}
