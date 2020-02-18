package vergecurrency.vergewallet.wallet


import vergecurrency.vergewallet.service.model.wallet.TxProposal
import vergecurrency.vergewallet.service.model.wallet.TxProposalErrorResponse
import vergecurrency.vergewallet.service.model.wallet.TxProposalResponse
import vergecurrency.vergewallet.wallet.int.WalletClientInterface

typealias CompletionType = (txp: TxProposalResponse, errorResponse: TxProposalErrorResponse, error: Exception) -> Unit

class TxTransponder(walletClient : WalletClientInterface) {
    enum class Step {
        publish,
        sign,
        broadcast

    }

    private  var step : Step = Step.publish
    private lateinit var previousTxp : TxProposalResponse
    private var wClient = walletClient

    public fun create(proposal: TxProposal, completion : CompletionType){
        wClient.createTxProposal(proposal, completion)
    }

}