package vergecurrency.vergewallet.view.ui.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider

import com.davidmiguel.numberkeyboard.NumberKeyboard
import com.davidmiguel.numberkeyboard.NumberKeyboardListener

import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.helpers.utils.UIUtils
import vergecurrency.vergewallet.model.WalletConfiguration
import vergecurrency.vergewallet.service.model.EncryptedPreferencesManager
import vergecurrency.vergewallet.view.base.BaseActivity
import vergecurrency.vergewallet.view.ui.activity.firstlaunch.PinSetActivity
import vergecurrency.vergewallet.view.ui.activity.settings.PaperkeyActivity
import vergecurrency.vergewallet.viewmodel.WalletConfigurationFactory

class PinPromptActivity : BaseActivity() {

    private var nextView: String? = null
    private var pin: String? = null
    lateinit var pinIVs: Array<ImageView?>
    private lateinit var mViewModel: WalletConfiguration
    private var pinLayout: GridLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this, WalletConfigurationFactory()).get(WalletConfiguration::class.java)

        nextView = intent.getStringExtra("nextView")
        setContentView(R.layout.activity_pin_prompt)
        initComponents()

    }

    private fun initComponents() {
        pin = ""

        pinLayout = findViewById(R.id.pin_digits_prompt)

        pinIVs = arrayOfNulls(6)
        //As a second priority, make this dynamic to allow for pin size change
        pinIVs[0] = findViewById(R.id.pin_one_prompt)
        pinIVs[1] = findViewById(R.id.pin_two_prompt)
        pinIVs[2] = findViewById(R.id.pin_three_prompt)
        pinIVs[3] = findViewById(R.id.pin_four_prompt)
        pinIVs[4] = findViewById(R.id.pin_five_prompt)
        pinIVs[5] = findViewById(R.id.pin_six_prompt)

        val numberKeyboard = findViewById<NumberKeyboard>(R.id.pin_number_pad)

        numberKeyboard.setLeftAuxButtonIcon(R.drawable.icon_fingerprint)
        numberKeyboard.setRightAuxButtonIcon(R.drawable.icon_backspace)

        numberKeyboard.setListener(nkl())

    }

    private fun nkl(): NumberKeyboardListener {
        return object : NumberKeyboardListener {
            override fun onNumberClicked(number: Int) {
                pin += (number.toString() + "")
                changePinColors(pin!!.length)

                if (pin!!.length == 6) {
                    if (pin == String(EncryptedPreferencesManager.pin!!)) {
                        redirectView()
                    } else {
                        Toast.makeText(applicationContext, "The pin inserted was wrong. Start Over", Toast.LENGTH_SHORT).show()
                        pinLayout!!.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.shake))
                        pin = ""
                        changePinColors(pin!!.length)
                    }
                }
            }

            private fun redirectView() {
                finish()
                when (nextView) {
                    "viewPassphrase" -> startActivity(Intent(applicationContext, PaperkeyActivity::class.java))
                    "performTransaction" -> {
                    }
                    "wallet" -> startActivity(Intent(applicationContext, WalletActivity::class.java))
                    "changePin" -> {
                        val i = Intent(applicationContext, PinSetActivity::class.java)
                        i.putExtra("origin", "settings")
                        startActivity(i)
                    }
                }
            }


            override fun onLeftAuxButtonClicked() {
                Toast.makeText(applicationContext, "Fingerprint not available yet.", Toast.LENGTH_LONG).show()
            }

            override fun onRightAuxButtonClicked() {
                if (pin!!.isNotEmpty()) {
                    pin = pin!!.substring(0, pin!!.length - 1)
                    changePinColors(pin!!.length)
                }
            }
        }
    }

    private fun changePinColors(numbers: Int) {
        for (i in pinIVs.indices) {
            if (i < numbers) {
                pinIVs[i]!!.backgroundTintList = ColorStateList.valueOf(getColorFromAttribute(R.attr.vg_primaryDark))
            } else {
                pinIVs[i]!!.backgroundTintList = ColorStateList.valueOf(getColorFromAttribute(R.attr.vg_primaryLight))
            }
        }
    }

    private fun getColorFromAttribute(attr: Int): Int {
        return ContextCompat.getColor(this, UIUtils.resolveAttr(attr, this))
    }

}

