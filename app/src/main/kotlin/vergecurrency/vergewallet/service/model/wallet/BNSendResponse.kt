package vergecurrency.vergewallet.service.model.wallet

import com.google.gson.Gson

class BNSendResponse {

    var txid: String? = null

    companion object {

        fun decode(JSON: String): BNSendResponse {
            return Gson().fromJson(JSON, BNSendResponse::class.java)
        }
    }
}
