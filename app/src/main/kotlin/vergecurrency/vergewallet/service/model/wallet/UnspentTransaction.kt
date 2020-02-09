package vergecurrency.vergewallet.service.model.wallet

import io.horizontalsystems.bitcoinkit.models.TransactionOutPoint
import io.horizontalsystems.bitcoinkit.models.TransactionOutput

class UnspentTransaction(val output: TransactionOutput, val outpoint: TransactionOutPoint)
