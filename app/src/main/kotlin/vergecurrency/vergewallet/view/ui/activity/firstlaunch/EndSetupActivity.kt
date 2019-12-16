package vergecurrency.vergewallet.view.ui.activity.firstlaunch

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

import vergecurrency.vergewallet.view.base.BaseActivity
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.view.ui.activity.WalletActivity
import vergecurrency.vergewallet.wallet.WalletManager

class EndSetupActivity : BaseActivity() {

    internal lateinit var openWallet: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet_ready)

        try {
            WalletManager.startWallet()
        } catch (e: Exception) {
            System.err.print(e)
            Toast.makeText(applicationContext, "Impossible to start wallet. Manuel you did some crap", Toast.LENGTH_LONG).show()
        }

        openWallet = findViewById(R.id.button_open_wallet)
        openWallet.setOnClickListener(openWalletButtonListener())

    }

    private fun openWalletButtonListener(): View.OnClickListener {
        return View.OnClickListener {
            finish()

            startActivity(Intent(applicationContext, WalletActivity::class.java))
        }

    }

    override fun onBackPressed() {
        //on purpose
    }
}
