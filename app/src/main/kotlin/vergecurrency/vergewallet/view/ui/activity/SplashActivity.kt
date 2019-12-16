package vergecurrency.vergewallet.view.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.testfairy.TestFairy

import java.io.IOException
import java.security.GeneralSecurityException

import vergecurrency.vergewallet.helpers.utils.LanguagesUtils
import vergecurrency.vergewallet.view.base.BaseActivity
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.service.model.PreferencesManager
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


        try {
            PreferencesManager.initEncryptedPreferences(this)
        } catch (e: GeneralSecurityException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        setContentView(R.layout.activity_splash)

        //Just to have the splash screen going briefly
        Handler().postDelayed({ this.startApplication() }, 2000)
    }

    private fun startApplication() {

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
