package vergecurrency.vergewallet.service.model.wallet

import io.horizontalsystems.bitcoinkit.models.TransactionInput
import io.horizontalsystems.bitcoinkit.models.TransactionOutPoint
import io.horizontalsystems.bitcoinkit.models.TransactionOutput
import org.bouncycastle.util.encoders.Hex
import vergecurrency.vergewallet.helpers.utils.DataUtils

class UnspentOutput {

    var address: String? = null
    var confirmations: Int = 0
    var satoshis: Long = 0
    var scriptPubKey: String? = null
    var txID: String? = null
    var vout: Int = 0
    var publicKeys: Array<String>? = null
    var path: String? = null
    var locked: Boolean = false


    //todo : fuck it I'll do it later. Another funny swift vs ios struggle
    //enum class CodingKeys : String, CodingKey {
    enum class CodingKeys {
        address,
        confirmations,
        satoshis,
        scriptPubKey,
        txID,
        vout,
        publicKeys,
        path,
        locked
    }

    //This can possibly be a big chunk of shit code. to test first priority.
    @Throws(InvalidScriptPubKeyHexException::class, InvalidTxIdHexException::class)
    fun asUnspentTransaction(): UnspentTransaction {
        val lockingScript: ByteArray
        val txid: ByteArray
        try {
            lockingScript = Hex.decode(scriptPubKey!!)
        } catch (e: Exception) {
            throw InvalidScriptPubKeyHexException(scriptPubKey!!)
        }

        try {
            txid = Hex.decode(txID!!)
        } catch (e: Exception) {
            throw InvalidTxIdHexException(txID!!)
        }

        val transactionOutput = TransactionOutput()
        transactionOutput.value = satoshis
        transactionOutput.lockingScript = lockingScript

        val txHash = DataUtils.reverse(txid)
        val transactionOutPoint = TransactionOutPoint(txHash, vout)

        return UnspentTransaction(transactionOutput, transactionOutPoint)
    }

    fun asInputTransaction(): TransactionInput {
        val txid: ByteArray
        try {
            txid = Hex.decode(txID!!)
        } catch (e: Exception) {
            throw InvalidTxIdHexException(txID!!)
        }

        val txHash = DataUtils.reverse(txid)
        val transactionOutPoint = TransactionOutPoint(txHash, vout)

        return TransactionInput().apply {
            //TODO : Not today
            previousOutputOP = transactionOutPoint
            sigScript = ByteArray(0)
            sequence = Long.MAX_VALUE
        }
    }


    internal inner class InvalidScriptPubKeyHexException(hex: String) : Exception(hex)

    internal inner class InvalidTxIdHexException(hex: String) : Exception(hex)
}
