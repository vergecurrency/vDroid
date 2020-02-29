package vergecurrency.vergewallet.helpers.utils

import android.annotation.SuppressLint
import android.content.Context
import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object TransactionUtils {
    const val EXTRA_TRANSACTION = "transaction"

    fun toFormattedDate(milliseconds: Long, appContext: Context): String {
        val date = Date(milliseconds * 1000)
        return String.format("%s %s", DateFormat.getMediumDateFormat(appContext).format(date), DateFormat.getTimeFormat(appContext).format(date));
    }
}


