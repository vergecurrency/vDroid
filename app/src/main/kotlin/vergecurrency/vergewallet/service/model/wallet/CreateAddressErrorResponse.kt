package vergecurrency.vergewallet.service.model.wallet

import com.google.gson.Gson

class CreateAddressErrorResponse {

    var code: String? = null
    var message: String? = null

    internal enum class Error {
        MAIN_ADDRESS_GAP_REACHED
    }

    companion object {

        fun decode(JSON: String): CreateAddressErrorResponse {
            return Gson().fromJson(JSON, CreateAddressErrorResponse::class.java)
        }
    }

}
