package vergecurrency.vergewallet.helpers.utils

import android.annotation.SuppressLint
import io.horizontalsystems.bitcoinkit.models.Transaction
import io.horizontalsystems.bitcoinkit.models.TransactionInput
import io.horizontalsystems.bitcoinkit.models.TransactionOutput
import io.horizontalsystems.bitcoinkit.transactions.scripts.Sighash
import java.text.SimpleDateFormat
import java.util.Date

object TransactionUtils {

        @SuppressLint("SimpleDateFormat")
        private val DATE_FORMATTER = SimpleDateFormat("dd LLLL yyyy")
        @SuppressLint("SimpleDateFormat")
        private val TIME_FORMATTER = SimpleDateFormat("HH:mm")
        const val EXTRA_TRANSACTION = "transaction"

        fun toFormattedDate(milliseconds: Long): String {
            val date = Date(milliseconds * 1000)
            return arrayOf(DATE_FORMATTER.format(date), "at", TIME_FORMATTER.format(date)).joinToString(" ")
        }

    fun getSignature(tx : Transaction, utxo : TransactionOutput, inputIndex : Int, hashType : Int) : ByteArray {

        val one = byteArrayOf(0x01.toByte())

        if (inputIndex < tx.inputs.count() ) {
            //return little-endian 0x01 instead of failing with error
            return one
        }

        if(hashType == Sighash.SINGLE && inputIndex < tx.outputs.count()) {
            return one
        }

        //val txSigSerializer =

        return ByteArray(0)

    }


    fun serializeSignature(inputs : Array<TransactionInput>, output : Array<TransactionOutput>, hashType: Int, inputIndex: Int ) :ByteArray {
        if (hashType == Sighash.ANYONECANPAY) {

        }


        return ByteArray(0)
    }

    tailrec fun modifiedInput(tx: Transaction, inputIndex: Int, i: Int) : TransactionInput {

        val txin = tx.inputs

        if (i == inputIndex) {

        }

        return  TransactionInput()
    }


    }


