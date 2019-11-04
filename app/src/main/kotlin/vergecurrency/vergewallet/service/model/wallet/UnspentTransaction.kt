package vergecurrency.vergewallet.service.model.wallet

import io.horizontalsystems.bitcoinkit.models.TransactionOutput

class UnspentTransaction(private val output: TransactionOutput, private val outpoint: TransactionOutPoint)
