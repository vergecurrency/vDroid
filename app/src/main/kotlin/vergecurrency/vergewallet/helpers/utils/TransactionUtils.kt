package vergecurrency.vergewallet.helpers.utils

import android.annotation.SuppressLint
import io.horizontalsystems.bitcoinkit.models.Transaction
import io.horizontalsystems.bitcoinkit.models.TransactionInput
import io.horizontalsystems.bitcoinkit.models.TransactionOutput
import io.horizontalsystems.bitcoinkit.transactions.scripts.Script
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
    }


