package vergecurrency.vergewallet.view.ui.activity.settings

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager

import vergecurrency.vergewallet.view.base.BaseActivity
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.view.ui.fragment.FragmentSend

class DonateActivity : BaseActivity() {

    private lateinit var donateButton: Button
    private lateinit var donateHeader: ImageView
    private lateinit var donateDesc1: TextView
    private lateinit var donateDesc2: TextView

    private lateinit var fl: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate)

        initComponents()
    }

    private fun initComponents() {
        donateHeader = findViewById(R.id.donate_header)
        donateDesc1 = findViewById(R.id.donate_desc1)
        donateDesc2 = findViewById(R.id.donate_desc2)

        fl = findViewById(R.id.donate_content)
        fl.visibility = View.INVISIBLE

        donateButton = findViewById(R.id.button_donate)
        donateButton.setOnClickListener(setOcl())
    }


    private fun setOcl(): View.OnClickListener {
        return View.OnClickListener {

            fl.visibility = View.VISIBLE
            donateDesc1.visibility = View.INVISIBLE
            donateDesc2.visibility = View.INVISIBLE
            donateHeader.visibility = View.INVISIBLE
            donateButton.visibility = View.INVISIBLE

            val fs = FragmentSend(getText(R.string.donate_donation_address).toString())

            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction()
                    .replace(R.id.donate_content, fs)
                    .commit()
        }
    }
}
