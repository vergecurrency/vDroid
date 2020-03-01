package vergecurrency.vergewallet.wallet


import vergecurrency.vergewallet.service.model.wallet.TxProposal
import vergecurrency.vergewallet.service.model.wallet.TxProposalErrorResponse
import vergecurrency.vergewallet.service.model.wallet.TxProposalResponse
import vergecurrency.vergewallet.wallet.int.WalletClientInterface

typealias CompletionType = (txp: TxProposalResponse?, errorResponse: TxProposalErrorResponse?, error: Exception?) -> Unit

 class TxTransponder(walletClient : WalletClientInterface) {
    enum class Step(val rawValue: Int) {
        publish(0),
        sign(1),
        broadcast(2)
    }

    private var wClient = walletClient
    private  var step : Step = Step.publish
    private var previousTxp : TxProposalResponse? = null
    private lateinit var completion : CompletionType

    public fun create(proposal: TxProposal, completion : CompletionType){
        wClient.createTxProposal(proposal, completion)
    }

    public fun send(txp: TxProposalResponse, completion: CompletionType) {
        this.completion = completion

        // Publish the tx proposal and start the sequence.
        wClient.publishTxProposal( txp, ::completionHandler)

    }

    private fun progress(txp: TxProposalResponse) {
        previousTxp = txp
        when (step) {
            Step.sign ->return  wClient.signTxProposal( txp, ::completionHandler)
            Step.broadcast ->return wClient.broadcastTxProposal(txp, ::completionHandler)
            else -> completionHandler(null, null, Exception("whoops"))
        }
    }

    private fun notifySystem() {
        when (step) {
            //Step.publish -> NotificationCenter.postNotification()post(name: .didPublishTx, object: self)
            //Step.sign -> NotificationCenter.default.post(name: .didSignTx, object: self)
            //Step.broadcast -> NotificationCenter.default.post(name: .didBroadcastTx, object: self)
        }
    }

    private fun completionHandler(txp: TxProposalResponse?,errorResponse: TxProposalErrorResponse?,error: Exception?)  {
        (errorResponse as? TxProposalErrorResponse)?.let { errorResponse ->
            completion(previousTxp,errorResponse,error)
            return
        }

        (error as? Exception)?.let {error ->
            completion(txp, errorResponse, error)
            return
        }

        // Notify the system of the successful step.
        notifySystem()

        // When there is a next step progress the sequence.

        var step = try{Step.values().get(step.rawValue+1)} catch(e: Exception){  null}
        if(step != null) {
            this.step = step
            return progress(txp!!)
        }

        // Complete the sequence.
        this.step = Step.publish
        this.previousTxp = null
        completion(txp, errorResponse, error)
    }
}

