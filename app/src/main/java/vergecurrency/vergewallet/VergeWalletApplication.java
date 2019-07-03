package vergecurrency.vergewallet;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.MainThread;

import io.horizontalsystems.bitcoinkit.BitcoinKit;
import vergecurrency.vergewallet.excpetion.DefaultUncaughtExceptionHandler;
import vergecurrency.vergewallet.helpers.utils.LanguagesUtils;
import vergecurrency.vergewallet.helpers.utils.UIUtils;
import vergecurrency.vergewallet.service.model.PreferencesManager;
import vergecurrency.vergewallet.wallet.WalletManager;

public class VergeWalletApplication extends Application implements Application.ActivityLifecycleCallbacks {

    public static String UNCAUGHT_EXCEPTION_CHANNEL_ID = "888671";
    public static String UNCAUGHT_EXCEPTION_CHANNEL_NAME = "Verge Android Wallet uncaught exception error channel";
    public static String UNCAUGHT_EXCEPTION_CHANNEL_DESCRIPTION = "Transmission of error reports";

    // Called when the application is starting, before any other application objects have been created.
    @Override
    public void onCreate() {
        super.onCreate();
        BitcoinKit.Companion.init(this);
        WalletManager.init();
        initExceptionHandler();
        registerActivityLifecycleCallbacks(this);
        createNotificationChannel();
    }

    @Override
    protected void attachBaseContext(Context base) {
        PreferencesManager.init(base);
        Context c = updateBaseContextLocale(base);
        UIUtils.setTheme(PreferencesManager.getCurrentTheme(),c,false);
        super.attachBaseContext(c);
    }

    private Context updateBaseContextLocale(Context context) {
        return LanguagesUtils.setLocale(context, PreferencesManager.getCurrentLanguage());
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