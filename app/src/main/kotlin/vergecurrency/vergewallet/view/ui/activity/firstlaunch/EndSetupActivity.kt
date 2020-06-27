package vergecurrency.vergewallet.view.ui.activity.firstlaunch

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.model.WalletConfiguration
import vergecurrency.vergewallet.service.model.EncryptedPreferencesManager
import vergecurrency.vergewallet.view.base.BaseActivity
import vergecurrency.vergewallet.view.ui.activity.WalletActivity
import vergecurrency.vergewallet.viewmodel.WalletConfigurationFactory
import vergecurrency.vergewallet.wallet.WalletManager
import java.util.*

class EndSetupActivity : BaseActivity() {

    internal lateinit var openWallet: Button
    private lateinit var mViewModel: WalletConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this, WalletConfigurationFactory()).get(WalletConfiguration::class.java)
        setContentView(R.layout.activity_wallet_ready)

        try {
            mViewModel.generateWalletId(this)
            mViewModel.setWalletName("default".toByteArray())
            val id = mViewModel.decrypt(mViewModel.getWalletId())
            EncryptedPreferencesManager.getOrCreateEncryptedSharedPreferences(this, UUID.fromString(String(id)))
            EncryptedPreferencesManager.walletId = id
            EncryptedPreferencesManager.walletName = mViewModel.decrypt(mViewModel.getWalletName())
            EncryptedPreferencesManager.mnemonic = Gson().toJson(mViewModel.getSeed().map { word -> mViewModel.decrypt(word) }.toTypedArray()).toByteArray();
            EncryptedPreferencesManager.passphrase = mViewModel.decrypt(mViewModel.getPassphrase())
            EncryptedPreferencesManager.pin = mViewModel.decrypt(mViewModel.getPin())
            WalletManager.startWallet(UUID.fromString(String(id)), true)
            WalletConfigurationFactory.clearWalletConfig();
        } catch (e: Exception) {
            System.err.print(e)
            Toast.makeText(applicationContext, "Impossible to start wallet. Manuel you did some crap", Toast.LENGTH_LONG).show()
        }

        openWallet = findViewById(R.id.button_open_wallet)
        openWallet.setOnClickListener(openWalletButtonListener())

    }

    private fun openWalletButtonListener(): View.OnClickListener {
        return View.OnClickListener {
            val intent = Intent(applicationContext, WalletActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        //on purpose
    }
}
