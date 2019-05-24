package vergecurrency.vergewallet.excpetion;

import android.content.Intent;
import android.os.Build;

import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import vergecurrency.vergewallet.view.ui.activity.base.VergeActivity;
import vergecurrency.vergewallet.view.ui.activity.error.ErrorRecoveryActivity;

public class DefaultExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static final Logger LOGGER = Logger.getLogger(DefaultExceptionHandler.class.getName());
    public static final String EXTRA_ERROR_CODE = "error-code";
    public static final String EXTRA_ERROR_MESSAGE = "error-message";
    public static final String EXTRA_ERROR_REPORT = "error-report";
    private static final String NEWLINE = "\n";
    private VergeActivity activity;

    public DefaultExceptionHandler(VergeActivity activity) {
        this.activity = activity;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        this.handle(e);
    }

    public void handle(Throwable e) {
        Intent intent = new Intent(this.activity, ErrorRecoveryActivity.class);
        if (e instanceof VergeException) {
            intent.putExtra(EXTRA_ERROR_CODE, ((VergeException) e).getError().getCode());
            intent.putExtra(EXTRA_ERROR_MESSAGE, ((VergeException) e).getError().getMessage());
        }

        StringWriter stackTrace = new StringWriter();
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

        //Create intent and start Activity
        intent.putExtra(EXTRA_ERROR_REPORT, report.toString());

        this.activity.startActivity(intent);
    }
}
