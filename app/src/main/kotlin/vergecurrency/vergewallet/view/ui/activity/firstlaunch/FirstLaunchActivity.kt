package vergecurrency.vergewallet.view.ui.activity.firstlaunch

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.text.HtmlCompat
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.view.base.BaseActivity

class FirstLaunchActivity : BaseActivity() {

    internal lateinit var bottomButton: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_launch)
        instantiateButtons()
    }

    private fun instantiateButtons() {

        //create "Create new wallet" button and its and listener
        bottomButton = findViewById(R.id.button_create_wallet)
        bottomButton.setText(HtmlCompat.fromHtml(resources.getString(R.string.first_launch_new_wallet_button), HtmlCompat.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE)
        bottomButton.setOnClickListener(newWalletOnClickListener())

        //create "Restore wallet"  button and its and listener.
        bottomButton = findViewById(R.id.button_restore_wallet)
        bottomButton.setText(HtmlCompat.fromHtml(resources.getString(R.string.first_launch_restore_wallet_button), HtmlCompat.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE)
        bottomButton.setOnClickListener(restoreWalletOnClickListener())
    }


    private fun newWalletOnClickListener(): View.OnClickListener {
        return View.OnClickListener {
            finish()
            val i = Intent(this, PinSetActivity::class.java)
            i.putExtra("origin", "firstLaunch")
            startActivity(i)
        }
    }

    private fun restoreWalletOnClickListener(): View.OnClickListener {
        return View.OnClickListener {
            //HERE TOO YOU ASSHOLE OF A DEV
        }
    }


}

