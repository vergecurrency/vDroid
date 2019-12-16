package vergecurrency.vergewallet.service.model.wallet

import com.google.gson.Gson

class TxProposalResponse {

    var createdOn: Int = 0
    var coin: String? = null
    var id: String? = null
    var network: String? = null
    var message: String? = null
    var inputs: Array<UnspentOutput>? = null
    var fee: Long = 0
    var status: String? = null
    var creatorId: String? = null
    var walletN: Long = 0
    var walletM: Long = 0
    var outputs: Array<TxOutput>? = null
    var amount: Long = 0
    var changeAddress: TxChangeAddress? = null
    var walletId: String? = null
    var requiredSignatures: Long = 0
    var version: Long = 0
    var isExcludeUnconfirmedUtxos: Boolean = false
    var addressType: String? = null
    var requiredRejections: Long = 0
    var outputOrder: IntArray? = null
    var inputPaths: Array<String>? = null

    companion object {

        fun decode(JSON: String): TxProposalResponse {
            return Gson().fromJson(JSON, TxProposalResponse::class.java)
        }
    }
}
