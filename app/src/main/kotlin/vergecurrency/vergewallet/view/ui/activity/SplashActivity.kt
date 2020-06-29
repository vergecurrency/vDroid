package vergecurrency.vergewallet.view.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.service.model.DataManager
import vergecurrency.vergewallet.service.model.PreferencesManager
import vergecurrency.vergewallet.service.model.network.layers.TorManager
import vergecurrency.vergewallet.view.base.BaseActivity
import vergecurrency.vergewallet.view.ui.activity.firstlaunch.FirstLaunchActivity
import vergecurrency.vergewallet.wallet.WalletManager
import java.util.*

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //Just to have the splash screen going briefly
        Handler().postDelayed({ this.startApplication() }, 500)
    }

    private fun startApplication() {
        try {
            TorManager.getInstance(applicationContext)

            if (PreferencesManager.usingTor) {
                TorManager.getInstance(null).startTor()

            }

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            PreferencesManager.usingTor = false
            Toast.makeText(applicationContext, "Tor could not launch. Switching to Clearnet. ALPHA ONLY.", Toast.LENGTH_LONG).show()
        }


        if (PreferencesManager.isFirstLaunch) {
            startActivity(Intent(applicationContext, FirstLaunchActivity::class.java))
            finish()
        } else {
            try {
                //init took place in VergeWalletApplication
                val keys: Set<UUID> = DataManager.getAllEncryptedPreferences(this).keys;
                if (keys.isNotEmpty()) {
                    val firstWallet = keys.toTypedArray()[0]
                    DataManager.loadWalletData(this, firstWallet);
                    WalletManager.startWallet(firstWallet, false)
                }
            } catch (e: Exception) {
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

            startActivity(Intent(applicationContext, WalletActivity::class.java))
            finish()
        }
    }

    override fun onBackPressed() {}
}
