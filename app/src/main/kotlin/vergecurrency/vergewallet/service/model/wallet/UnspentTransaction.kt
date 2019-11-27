package vergecurrency.vergewallet.service.model.wallet

import io.horizontalsystems.bitcoinkit.models.TransactionOutput
import io.horizontalsystems.bitcoinkit.models.TransactionOutPoint

class UnspentTransaction(val output: TransactionOutput, val outpoint: TransactionOutPoint)
