package vergecurrency.vergewallet.helpers.utils

import android.content.Context
import android.text.format.DateFormat
import java.util.*

object TransactionUtils {
    const val EXTRA_TRANSACTION = "transaction"

    fun toFormattedDate(milliseconds: Long, context: Context): String {
        val date = Date(milliseconds * 1000)
        return String.format("%s %s", DateFormat.getMediumDateFormat(context).format(date), DateFormat.getTimeFormat(context).format(date));
    }
}


