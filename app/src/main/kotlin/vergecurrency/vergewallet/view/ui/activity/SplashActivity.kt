package vergecurrency.vergewallet.view.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import io.realm.Realm
import io.realm.RealmConfiguration
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.helpers.utils.WalletDataIdentifierUtils
import vergecurrency.vergewallet.service.model.EncryptedPreferencesManager
import vergecurrency.vergewallet.service.model.PreferencesManager
import vergecurrency.vergewallet.service.model.VDroidRealmModule
import vergecurrency.vergewallet.service.model.network.layers.TorManager
import vergecurrency.vergewallet.view.base.BaseActivity
import vergecurrency.vergewallet.view.ui.activity.firstlaunch.FirstLaunchActivity
import vergecurrency.vergewallet.wallet.WalletManager

class SplashActivity : BaseActivity() {
    //DbOpenHelper dbOpenHelper;
    //private AbstractDaoSession daoSession;
    private var xvgDataRealm: Realm? = null;

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
            EncryptedPreferencesManager.usingTor = false

            if (EncryptedPreferencesManager.usingTor) {
                TorManager.startTor(this)

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
                WalletManager.startWallet()
                getOrCreateVergeDataRealm()
                EncryptedPreferencesManager.getOrCreateEncryptedSharedPreferences(applicationContext, "wallet")
            } catch (e: Exception) {
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

            startActivity(Intent(applicationContext, WalletActivity::class.java))
            finish()
        }
    }

    private fun getOrCreateVergeDataRealm() {
        val config = RealmConfiguration.Builder()
                .name(WalletDataIdentifierUtils.getRealmFileNameByUsersWalletName(EncryptedPreferencesManager.walletName!!))
                .encryptionKey(EncryptedPreferencesManager.realmEncryptionKey!!.toByteArray())
                .schemaVersion(0)
                .modules(VDroidRealmModule())
                .build()
        this.xvgDataRealm = Realm.getInstance(config);
    }

    override fun onBackPressed() {}
}
