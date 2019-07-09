package vergecurrency.vergewallet.helpers.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class TransactionUtils {
    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd LLLL yyyy");
    public static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("HH:mm");
    public static final String EXTRA_TRANSACTION = "transaction";

    public static String toFormattedDate(long milliseconds) {
        Date date = new Date(milliseconds * 1000);
        return String.join(" ", DATE_FORMATTER.format(date), "at", TIME_FORMATTER.format(date));
    }

}
