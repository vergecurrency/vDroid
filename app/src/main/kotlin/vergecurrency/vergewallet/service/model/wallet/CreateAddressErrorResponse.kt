package vergecurrency.vergewallet.service.model.wallet

class CreateAddressErrorResponse {

    var code: String? = null
    var message: String? = null

    internal enum class Error {
        MAIN_ADDRESS_GAP_REACHED
    }

}
