package vergecurrency.vergewallet

import android.app.Activity
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle

import androidx.annotation.MainThread

import io.horizontalsystems.bitcoinkit.BitcoinKit
import vergecurrency.vergewallet.exception.DefaultUncaughtExceptionHandler
import vergecurrency.vergewallet.helpers.utils.LanguagesUtils
import vergecurrency.vergewallet.helpers.utils.UIUtils
import vergecurrency.vergewallet.service.model.PreferencesManager
import vergecurrency.vergewallet.wallet.WalletManager

class VergeWalletApplication : Application(), Application.ActivityLifecycleCallbacks {

    // Called when the application is starting, before any other application objects have been created.
    override fun onCreate() {
        super.onCreate()
        BitcoinKit.init(this)
        WalletManager.init()
        initExceptionHandler()
        registerActivityLifecycleCallbacks(this)
        createNotificationChannel()
    }

    override fun attachBaseContext(base: Context) {
        PreferencesManager.init(base)
        val c = updateBaseContextLocale(base)
        UIUtils.setTheme(PreferencesManager.currentTheme!!, c, false)
        super.attachBaseContext(c)
    }

    private fun updateBaseContextLocale(context: Context): Context {
        return LanguagesUtils.setLocale(context, PreferencesManager.currentLanguage!!)
    }

    override fun onTerminate() {
        super.onTerminate()
        unregisterActivityLifecycleCallbacks(this)
    }

    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }


    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        DefaultUncaughtExceptionHandler.instance!!.setCurrentActivity(activity)
    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = UNCAUGHT_EXCEPTION_CHANNEL_NAME
            val description = UNCAUGHT_EXCEPTION_CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(UNCAUGHT_EXCEPTION_CHANNEL_ID, name, importance)
            channel.description = description
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager!!.createNotificationChannel(channel)
        }
    }

    @MainThread
    private fun initExceptionHandler() {
        if (DefaultUncaughtExceptionHandler.instance == null) {
            DefaultUncaughtExceptionHandler.init()
            //Set Exception Handler on main thread
            Thread.setDefaultUncaughtExceptionHandler(DefaultUncaughtExceptionHandler.instance)
        }
    }

    companion object {

        var UNCAUGHT_EXCEPTION_CHANNEL_ID = "888671"
        var UNCAUGHT_EXCEPTION_CHANNEL_NAME = "Verge Android Wallet uncaught exception error channel"
        var UNCAUGHT_EXCEPTION_CHANNEL_DESCRIPTION = "Transmission of error reports"
    }
}