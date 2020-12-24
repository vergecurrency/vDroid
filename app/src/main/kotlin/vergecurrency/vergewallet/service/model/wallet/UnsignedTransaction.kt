package vergecurrency.vergewallet.service.model.wallet

import io.horizontalsystems.bitcoincore.models.Transaction

data class UnsignedTransaction(val tx: Transaction, val utxos: Array<UnspentTransaction>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UnsignedTransaction

        if (tx != other.tx) return false
        if (!utxos.contentEquals(other.utxos)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = tx.hashCode()
        result = 31 * result + utxos.contentHashCode()
        return result
    }
}