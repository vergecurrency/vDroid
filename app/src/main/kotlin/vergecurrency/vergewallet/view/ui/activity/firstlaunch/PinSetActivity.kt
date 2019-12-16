package vergecurrency.vergewallet.view.ui.activity.firstlaunch

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat

import com.davidmiguel.numberkeyboard.NumberKeyboard
import com.davidmiguel.numberkeyboard.NumberKeyboardListener

import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.helpers.utils.UIUtils
import vergecurrency.vergewallet.view.base.BaseActivity

class PinSetActivity : BaseActivity() {

    private var numberKeyboard: NumberKeyboard? = null

    private lateinit var pinIVs: Array<ImageView?>

    private var pin: String? = null
    private var origin: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        origin = intent.getStringExtra("origin")

        setContentView(R.layout.activity_pin_set)
        initComponents()
    }

    private fun initComponents() {
        pin = ""

        pinIVs = arrayOfNulls<ImageView>(6)
        //As a second priority, make this dynamic to allow for pin size change
        pinIVs[0] = findViewById(R.id.pin_one)
        pinIVs[1] = findViewById(R.id.pin_two)
        pinIVs[2] = findViewById(R.id.pin_three)
        pinIVs[3] = findViewById(R.id.pin_four)
        pinIVs[4] = findViewById(R.id.pin_five)
        pinIVs[5] = findViewById(R.id.pin_six)

        numberKeyboard = findViewById(R.id.pin_number_pad)

        numberKeyboard!!.setLeftAuxButtonIcon(R.drawable.icon_fingerprint)
        numberKeyboard!!.setRightAuxButtonIcon(R.drawable.icon_backspace)

        numberKeyboard!!.setListener(nkl())

    }

    private fun nkl(): NumberKeyboardListener {
        return object : NumberKeyboardListener {
            override fun onNumberClicked(number: Int) {
                pin += (number.toString() + "")
                changePinColors(pin!!.length)

                //replace with preferences manager pinCount() once implemented feature
                if (pin!!.length == 6) {
                    finish()
                    val intent = Intent(applicationContext, PinConfirmActivity::class.java)
                    intent.putExtra("pin", pin)
                    intent.putExtra("origin", origin)
                    startActivity(intent)
                }
            }

            override fun onLeftAuxButtonClicked() {
                Toast.makeText(applicationContext, "Fingerprint not available yet.", Toast.LENGTH_LONG).show()
            }

            override fun onRightAuxButtonClicked() {
                if (pin!!.length > 0) {
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

