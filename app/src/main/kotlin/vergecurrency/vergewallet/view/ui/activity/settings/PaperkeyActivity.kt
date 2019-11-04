package vergecurrency.vergewallet.view.ui.activity.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button

import vergecurrency.vergewallet.view.base.BaseActivity
import vergecurrency.vergewallet.R

class PaperkeyActivity : BaseActivity() {

    private lateinit var showPaperKeyButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paperkey)

        showPaperKeyButton = findViewById(R.id.activity_paperkey_button)
        showPaperKeyButton.setOnClickListener(onShowPaperKeyListener())
    }


    internal fun onShowPaperKeyListener(): View.OnClickListener {

        return View.OnClickListener {
            finish()
            startActivity(Intent(applicationContext, ShowPaperkeyActivity::class.java))
        }
    }
}
