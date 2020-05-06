package vergecurrency.vergewallet.service.model.wallet

import com.google.gson.Gson
import io.horizontalsystems.bitcoinkit.io.BitcoinInput
import io.horizontalsystems.bitcoinkit.models.TransactionInput
import io.horizontalsystems.bitcoinkit.models.TransactionOutPoint
import io.horizontalsystems.bitcoinkit.models.TransactionOutput
import org.bouncycastle.util.encoders.Hex
import vergecurrency.vergewallet.helpers.utils.DataUtils


class BNTransaction {

    private val chain: String? = null
    private val network: String? = null
    private val coinbase: Boolean = false
    private val mintIndex: Int = 0
    private val spentTxid: String? = null
    private val mintTxid: String? = null
    private val mintHeight: Long = 0
    private val spentHeight: Long = 0
    private val address: String? = null
    private val script: String? = null
    private val value: Long = 0
    private val confirmations: Long = 0

    companion object {

        fun decode(JSON: String): BNTransaction {
            return Gson().fromJson(JSON, BNTransaction::class.java)
        }
    }


    //This can possibly be a big chunk of shit code. to test first priority.
    @Throws(InvalidScriptPubKeyHexException::class, InvalidTxIdHexException::class)
    fun asUnspentTransaction(): UnspentTransaction {
        val lockingScript: ByteArray
        val txid: ByteArray
        try {
            lockingScript = Hex.decode(script!!)
        } catch (e: Exception) {
            throw InvalidScriptPubKeyHexException(script!!)
        }

        try {
            txid = Hex.decode(mintTxid!!)
        } catch (e: Exception) {
            throw InvalidTxIdHexException(mintTxid!!)
        }

        val transactionOutput = TransactionOutput()
        transactionOutput.value = value
        transactionOutput.lockingScript = lockingScript
        val txHash = DataUtils.reverse(txid)
        val transactionOutPoint = TransactionOutPoint(txHash, mintIndex)

        return UnspentTransaction(transactionOutput, transactionOutPoint)
    }

    @Throws(InvalidTxIdHexException::class)
    fun asInputTransaction(): TransactionInput {
        val txid: ByteArray
        try {
            txid = Hex.decode(mintTxid!!)
        } catch (e: Exception) {
            throw InvalidTxIdHexException(mintTxid!!)
        }

        val txHash = DataUtils.reverse(txid)
        val transactionOutPoint = TransactionOutPoint(txHash, mintIndex)

        //This is probs bullshit but the BitcoinKit on Android and Swift don't correspond on this one so let's see...
        return TransactionInput(BitcoinInput(transactionOutPoint.serialize()))

    }


    internal inner class InvalidScriptPubKeyHexException(hex: String) : Exception(hex)

    internal inner class InvalidTxIdHexException(hex: String) : Exception(hex)

}
