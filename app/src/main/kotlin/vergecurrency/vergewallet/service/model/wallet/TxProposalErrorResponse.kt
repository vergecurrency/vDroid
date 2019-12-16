package vergecurrency.vergewallet.service.model.wallet

class TxProposalErrorResponse {

    var code: String? = null
    var message: String? = null

    internal enum class Error {
        BAD_SIGNATURES
    }
}
