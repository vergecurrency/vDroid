package vergecurrency.vergewallet.view.ui.activity.firstlaunch

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider

import com.davidmiguel.numberkeyboard.NumberKeyboard
import com.davidmiguel.numberkeyboard.NumberKeyboardListener

import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.helpers.utils.UIUtils
import vergecurrency.vergewallet.view.base.BaseActivity
import vergecurrency.vergewallet.viewmodel.PinVerificationViewModel

class PinConfirmActivity : BaseActivity() {

    private var pinToValidate: String? = null
    private var pin: String? = null
    private var origin: String? = null
    private lateinit var pinIVs: Array<ImageView?>
    private var mViewModel: PinVerificationViewModel? = null
    private var buttonContinue: Button? = null
    private var confirmLayout: RelativeLayout? = null
    private var numberKeyboard: NumberKeyboard? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pinToValidate = intent.getStringExtra("pin")
        mViewModel = ViewModelProvider(this).get(PinVerificationViewModel::class.java)

        setContentView(R.layout.activity_pin_confirm)
        initComponents()

        origin = intent.getStringExtra("origin")
    }

    private fun initComponents() {
        pin = ""

        pinIVs = arrayOfNulls(6)
        //As a second priority, make this dynamic to allow for pin size change
        pinIVs[0] = findViewById(R.id.pin_one_conf)
        pinIVs[1] = findViewById(R.id.pin_two_conf)
        pinIVs[2] = findViewById(R.id.pin_three_conf)
        pinIVs[3] = findViewById(R.id.pin_four_conf)
        pinIVs[4] = findViewById(R.id.pin_five_conf)
        pinIVs[5] = findViewById(R.id.pin_six_conf)

        numberKeyboard = findViewById(R.id.pin_number_pad)

        numberKeyboard!!.setLeftAuxButtonIcon(R.drawable.icon_fingerprint)
        numberKeyboard!!.setRightAuxButtonIcon(R.drawable.icon_backspace)

        numberKeyboard!!.setListener(nkl())

        buttonContinue = findViewById(R.id.pin_confirmed_button)
        buttonContinue!!.setOnClickListener(confirmButtonListener())

        confirmLayout = findViewById(R.id.pin_confirmed_layout)

    }

    private fun nkl(): NumberKeyboardListener {
        return object : NumberKeyboardListener {
            override fun onNumberClicked(number: Int) {
                pin += (number.toString() + "")
                changePinColors(pin!!.length)

                if (pin!!.length == 6 && pin == pinToValidate) {
                    numberKeyboard!!.visibility = View.GONE
                    confirmLayout!!.visibility = View.VISIBLE
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

    private fun confirmButtonListener(): View.OnClickListener {
        return View.OnClickListener {
            mViewModel!!.setPin(pin!!.toCharArray())
            if (origin == "firstLaunch") {
                startActivity(Intent(applicationContext, PaperkeyInstructionsActivity::class.java))
            } else if (origin == "settings") {
                finish()
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

