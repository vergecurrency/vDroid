package vergecurrency.vergewallet.service.model.wallet

import io.horizontalsystems.bitcoincore.models.TransactionOutPoint
import io.horizontalsystems.bitcoincore.models.TransactionOutput

class UnspentTransaction(val output: TransactionOutput, val outpoint: TransactionOutPoint)
