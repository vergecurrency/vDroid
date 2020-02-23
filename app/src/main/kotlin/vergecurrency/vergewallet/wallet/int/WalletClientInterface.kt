package vergecurrency.vergewallet.wallet.int

import vergecurrency.vergewallet.service.model.wallet.*

interface WalletClientInterface {
    fun createWallet(walletName: String, copayerName: String, m: Int, n: Int, options: WalletOptions?, completion: (Exception?, String?) -> Void) {}
    fun joinWallet(walletIdentifier: String, completion: (Exception?) -> Void) {}
    fun openWallet(completion:  (Exception?) -> Void) {}
    fun scanAddresses(completion:  (Exception?) -> Void) {}
    fun createAddress(completion:  (Exception?, AddressInfo?, CreateAddressErrorResponse?) -> Void) {}
    fun getBalance(completion:  (Exception?, WalletBalanceInfo?) -> Void) {}
    fun  getMainAddresses(options: WalletAddressesOptions?, completion:  (_address : Array<AddressInfo>) -> Void) {}
    fun  getTxHistory(skip: Int? = null, limit: Int? = null, completion:  (_txs : Array<TxHistory>) -> Void) {}
    fun  getUnspentOutputs(address: String? = null, completion:  (_addresses : Array<UnspentOutput>) -> Void) {}
    fun  getSendMaxInfo(completion:  (SendMaxInfo?) -> Void) {}
    fun  createTxProposal(proposal: TxProposal, completion:  (TxProposalResponse?, TxProposalErrorResponse?, Exception?) -> Unit) {}
    fun  publishTxProposal(txp: TxProposalResponse, completion:  (TxProposalResponse?, TxProposalErrorResponse?, Exception?) -> Unit) {}
    fun  signTxProposal(txp: TxProposalResponse, completion:  (TxProposalResponse?, TxProposalErrorResponse?, Exception?) -> Unit) {}
    fun  broadcastTxProposal(txp: TxProposalResponse, completion:  (TxProposalResponse?, TxProposalErrorResponse?, Exception?) -> Unit) {}
    fun  rejectTxProposal(txp: TxProposalResponse, completion:  (Exception?) -> Void) {}
    fun  deleteTxProposal(txp: TxProposalResponse, completion:  (Exception?) -> Void) {}
    fun  getTxProposals(completion:  (_response : Array<TxProposalResponse>, Exception?) -> Void) {}
    fun  resetServiceUrl(baseUrl: String) {}
}