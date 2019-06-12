package vergecurrency.vergewallet.wallet;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.MainThread;

import vergecurrency.vergewallet.excpetion.DefaultUncaughtExceptionHandler;

public class VergeWalletApplication extends Application implements Application.ActivityLifecycleCallbacks {

    public static String UNCAUGHT_EXCEPTION_CHANNEL_ID = "888671";
    public static String UNCAUGHT_EXCEPTION_CHANNEL_NAME = "Verge Android Wallet uncaught exception error channel";
    public static String UNCAUGHT_EXCEPTION_CHANNEL_DESCRIPTION = "Transmission of error reports";
    public static VergeWalletApplication INSTANCE;


    public static VergeWalletApplication getInstance() {
        return INSTANCE;
    }

    // Called when the application is starting, before any other application objects have been created.
    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        this.initExceptionHandler();
        registerActivityLifecycleCallbacks(this);
        createNotificationChannel();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterActivityLifecycleCallbacks(this);
    }

    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        DefaultUncaughtExceptionHandler.getInstance().setCurrentActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = UNCAUGHT_EXCEPTION_CHANNEL_NAME;
            String description = UNCAUGHT_EXCEPTION_CHANNEL_DESCRIPTION;
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(UNCAUGHT_EXCEPTION_CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @MainThread
    private void initExceptionHandler() {
        if (DefaultUncaughtExceptionHandler.getInstance() == null) {
            DefaultUncaughtExceptionHandler.init();
            //Set Exception Handler on main thread
            Thread.setDefaultUncaughtExceptionHandler(DefaultUncaughtExceptionHandler.getInstance());
        }
    }
}