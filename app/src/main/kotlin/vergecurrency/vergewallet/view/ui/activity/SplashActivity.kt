package vergecurrency.vergewallet.view.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.service.model.EncryptedPreferencesManager
import vergecurrency.vergewallet.service.model.PreferencesManager
import vergecurrency.vergewallet.service.model.network.layers.TorManager
import vergecurrency.vergewallet.view.base.BaseActivity
import vergecurrency.vergewallet.view.ui.activity.firstlaunch.FirstLaunchActivity
import vergecurrency.vergewallet.wallet.WalletManager

class SplashActivity : BaseActivity() {
    //DbOpenHelper dbOpenHelper;
    //private AbstractDaoSession daoSession;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //init db
        //dbOpenHelper = new DbOpenHelper(this,"verge.db",1);
        //Database db = dbOpenHelper.getWritableDb();
        //daoSession = new AbstractDaoMaster;
        setContentView(R.layout.activity_splash)
        //Just to have the splash screen going briefly
        Handler().postDelayed({ r.run() }, 500)
    }

    private var r: Runnable = Runnable {
        try {
            TorManager.getInstance(applicationContext)

            //TODO TESTING only
            EncryptedPreferencesManager.usingTor = true

            if (EncryptedPreferencesManager.usingTor) {


                TorManager.getInstance(null).execute()

            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            EncryptedPreferencesManager.usingTor = false
            Toast.makeText(applicationContext, "Tor could not launch. Switching to Clearnet. ALPHA ONLY.", Toast.LENGTH_LONG).show()
        }


        if (PreferencesManager.isFirstLaunch) {
            startActivity(Intent(applicationContext, FirstLaunchActivity::class.java))
            finish()
        } else {
            try {
                //init took place in VergeWalletApplication
                WalletManager.startWallet()
            } catch (e: Exception) {
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

            startActivity(Intent(applicationContext, WalletActivity::class.java))
            finish()
        }

    }

    override fun onBackPressed() {}
}
