package vergecurrency.vergewallet.view.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import io.realm.Realm
import io.realm.RealmConfiguration
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.helpers.utils.WalletDataIdentifierUtils
import vergecurrency.vergewallet.service.model.DataManager
import vergecurrency.vergewallet.service.model.EncryptedPreferencesManager
import vergecurrency.vergewallet.service.model.PreferencesManager
import vergecurrency.vergewallet.service.model.VDroidRealmModule
import vergecurrency.vergewallet.service.model.network.layers.TorManager
import vergecurrency.vergewallet.view.base.BaseActivity
import vergecurrency.vergewallet.view.ui.activity.firstlaunch.FirstLaunchActivity
import vergecurrency.vergewallet.wallet.WalletManager
import java.util.*

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
        Handler().postDelayed({ this.startApplication() }, 500)
    }

    private fun startApplication() {
        try {
            //TODO TESTING only
            /*EncryptedPreferencesManager.usingTor = false

            if (EncryptedPreferencesManager.usingTor) {
                //TorManager.startTor(this)

            }*/
            val keys: Set<UUID> = DataManager.getAllEncryptedPreferences(this).keys;
            if (keys.size > 0) {
                val firstWallet = keys.toTypedArray()[0]
                DataManager.loadWalletData(this, firstWallet);
                WalletManager.startWallet(firstWallet, false)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace();
        }


        if (PreferencesManager.isFirstLaunch) {
            startActivity(Intent(applicationContext, FirstLaunchActivity::class.java))
            finish()
        } else {
            try {
                //init took place in VergeWalletApplication

            } catch (e: Exception) {
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

            startActivity(Intent(applicationContext, WalletActivity::class.java))
            finish()
        }
    }

    override fun onBackPressed() {}
}
