package vergecurrency.vergewallet.service.model.wallet

import com.google.gson.Gson

class TxProposalErrorResponse {

    var code: String? = null
    var message: String? = null

    internal enum class Error {
        BAD_SIGNATURES
    }

    companion object {

        fun decode(JSON: String): TxProposalErrorResponse {
            return Gson().fromJson(JSON, TxProposalErrorResponse::class.java)
        }

    }
}
