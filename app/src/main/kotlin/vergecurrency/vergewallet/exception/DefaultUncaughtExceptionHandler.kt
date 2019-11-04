@file:JvmName("DefaultUncaughtExceptionHandler")

package vergecurrency.vergewallet.exception

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.os.Build

import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

import java.io.PrintWriter
import java.io.StringWriter
import java.util.concurrent.atomic.AtomicInteger
import java.util.logging.Level
import java.util.logging.Logger

import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.VergeWalletApplication.Companion.UNCAUGHT_EXCEPTION_CHANNEL_ID
import vergecurrency.vergewallet.view.ui.activity.error.ErrorRecoveryActivity


class DefaultUncaughtExceptionHandler private constructor() : Thread.UncaughtExceptionHandler {
    private var currentActivity: Activity? = null

    override fun uncaughtException(t: Thread, e: Throwable) {
        this.handle(e)
    }

    private fun handle(e: Throwable) {
        val stackTrace = StringWriter()
        e.printStackTrace(PrintWriter(stackTrace))

        //Logger
        LOGGER.log(Level.SEVERE, stackTrace.toString())

        val intent = Intent(currentActivity, ErrorRecoveryActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        //Schedule App restart containing error report
        val report = "************ CAUSE OF ERROR ************" +
                NEWLINE +
                NEWLINE +
                stackTrace.toString() +
                NEWLINE +
                "************ DEVICE INFORMATION ***********" +
                NEWLINE +
                "Brand: " +
                Build.BRAND +
                NEWLINE +
                "Device: " +
                Build.DEVICE +
                NEWLINE +
                "Model: " +
                Build.MODEL +
                NEWLINE +
                "Id: " +
                Build.ID +
                NEWLINE +
                "Product: " +
                Build.PRODUCT +
                NEWLINE +
                NEWLINE +
                "************ FIRMWARE ************" +
                NEWLINE +
                "SDK: " +
                Build.VERSION.CODENAME +
                " - " +
                Build.VERSION.SDK_INT +
                NEWLINE +
                "Release: " +
                Build.VERSION.RELEASE +
                NEWLINE +
                "Incremental: " +
                Build.VERSION.INCREMENTAL +
                NEWLINE
        intent.putExtra(EXTRA_ERROR_REPORT, report)

        val pendingIntent = PendingIntent.getActivity(currentActivity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        //Prepare Error Notification
        val builder = NotificationCompat.Builder(currentActivity!!, UNCAUGHT_EXCEPTION_CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_notification)
                .setContentTitle(currentActivity!!.resources.getString(R.string.error_uncaught_exception))
                .setCategory(NotificationCompat.CATEGORY_ERROR)
                .addAction(R.drawable.common_google_signin_btn_icon_dark_normal_background, currentActivity!!.resources.getString(R.string.error_uncaught_exception_show_report),
                        pendingIntent)
                .setFullScreenIntent(pendingIntent, true).setColorized(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
        val notificationManager = NotificationManagerCompat.from(currentActivity!!)

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(errorNotificationId, builder.build())
        currentActivity!!.finish()
        System.exit(2)
    }

    fun setCurrentActivity(currentActivity: Activity) {
        this.currentActivity = currentActivity
    }

    companion object {
        private val notificationNumber = AtomicInteger(0)
        private val LOGGER = Logger.getLogger(DefaultUncaughtExceptionHandler::class.java.name)
        val EXTRA_ERROR_REPORT = "error-report"
        private val NEWLINE = "\n"
        var instance: DefaultUncaughtExceptionHandler? = null
            private set

        //--------Singleton methods
        fun init() {
            if (instance != null) {
                throw AssertionError("You already initialized an object of this type")
            }
            instance = DefaultUncaughtExceptionHandler()
        }

        private val errorNotificationId: Int
            get() = notificationNumber.incrementAndGet()
    }
}
