package vergecurrency.vergewallet.excpetion;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.view.ui.activity.error.ErrorRecoveryActivity;

import static vergecurrency.vergewallet.wallet.VergeWalletApplication.UNCAUGHT_EXCEPTION_CHANNEL_ID;

public class DefaultUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    private final static AtomicInteger notificationNumber = new AtomicInteger(0);
    private static final Logger LOGGER = Logger.getLogger(DefaultUncaughtExceptionHandler.class.getName());
    public static final String EXTRA_ERROR_REPORT = "error-report";
    private static final String NEWLINE = "\n";
    private Activity currentActivity;
    static DefaultUncaughtExceptionHandler INSTANCE = null;


    private DefaultUncaughtExceptionHandler() {
    }

    //--------Singleton methods
    public static DefaultUncaughtExceptionHandler init() {
        if (INSTANCE != null) {
            throw new AssertionError("You already initialized an object of this type");
        }
        return INSTANCE = new DefaultUncaughtExceptionHandler();
    }

    public static DefaultUncaughtExceptionHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        this.handle(e);
    }

    private void handle(Throwable e) {
        StringWriter stackTrace = new StringWriter();
        e.printStackTrace(new PrintWriter(stackTrace));
        StringBuilder report = new StringBuilder();
        report.append("************ CAUSE OF ERROR ************");
        report.append(NEWLINE);
        report.append(NEWLINE);
        report.append(stackTrace.toString());
        report.append(NEWLINE);
        report.append("************ DEVICE INFORMATION ***********");
        report.append(NEWLINE);
        report.append("Brand: ");
        report.append(Build.BRAND);
        report.append(NEWLINE);
        report.append("Device: ");
        report.append(Build.DEVICE);
        report.append(NEWLINE);
        report.append("Model: ");
        report.append(Build.MODEL);
        report.append(NEWLINE);
        report.append("Id: ");
        report.append(Build.ID);
        report.append(NEWLINE);
        report.append("Product: ");
        report.append(Build.PRODUCT);
        report.append(NEWLINE);
        report.append(NEWLINE);
        report.append("************ FIRMWARE ************");
        report.append(NEWLINE);
        report.append("SDK: ");
        report.append(Build.VERSION.CODENAME);
        report.append(" - ");
        report.append(Build.VERSION.SDK_INT);
        report.append(NEWLINE);
        report.append("Release: ");
        report.append(Build.VERSION.RELEASE);
        report.append(NEWLINE);
        report.append("Incremental: ");
        report.append(Build.VERSION.INCREMENTAL);
        report.append(NEWLINE);

        //Logger
        LOGGER.log(Level.SEVERE, stackTrace.toString());

        Intent intent = new Intent(currentActivity, ErrorRecoveryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //Schedule App restart containing error report
        intent.putExtra(EXTRA_ERROR_REPORT, report.toString());
        PendingIntent pendingIntent = PendingIntent.getActivity(currentActivity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //Prepare Error Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(currentActivity, UNCAUGHT_EXCEPTION_CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_notification)
                .setContentTitle(currentActivity.getResources().getString(R.string.error_uncaught_exception))
                .setCategory(NotificationCompat.CATEGORY_ERROR)
                .addAction(R.drawable.common_google_signin_btn_icon_dark_normal_background, currentActivity.getResources().getString(R.string.error_uncaught_exception_show_report),
                        pendingIntent)
                .setFullScreenIntent(pendingIntent, true).setColorized(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(currentActivity);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(getErrorNotificationId(), builder.build());
        currentActivity.finish();
        System.exit(2);
    }

    private static int getErrorNotificationId() {
        return notificationNumber.incrementAndGet();
    }

    public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }
}
