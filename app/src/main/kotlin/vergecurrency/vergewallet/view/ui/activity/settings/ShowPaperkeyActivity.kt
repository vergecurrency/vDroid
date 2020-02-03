package vergecurrency.vergewallet.view.ui.activity.settings

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.view.base.BaseActivity
import vergecurrency.vergewallet.viewmodel.ShowPaperkeyViewModel

class ShowPaperkeyActivity : BaseActivity() {

    private var mViewModel: ShowPaperkeyViewModel? = null
    private var seedTextView: TextView? = null
    private var closeButton: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        mViewModel = ViewModelProviders.of(this).get(ShowPaperkeyViewModel::class.java)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paperkey_shown)
        initComponents()
    }


    private fun initComponents() {
        seedTextView = findViewById(R.id.paperkey_shown_pk)
        seedTextView!!.text = mViewModel!!.seedAsText
        closeButton = findViewById(R.id.paperkey_shown_close_button)
        closeButton!!.setOnClickListener(onCloseClickListener())
    }

    internal fun onCloseClickListener(): View.OnClickListener {
        return View.OnClickListener { finish() }
    }

}
