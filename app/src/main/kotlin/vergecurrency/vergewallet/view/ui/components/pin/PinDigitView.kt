package vergecurrency.vergewallet.view.ui.components.pin

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView

import org.w3c.dom.Text

import vergecurrency.vergewallet.R


class PinDigitView(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    private val mTextViewDigit: TextView
    private val mTextViewLetters: TextView


    init {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.compound_pin_digit, this, true)

        mTextViewDigit = getChildAt(0) as TextView
        mTextViewLetters = getChildAt(1) as TextView
    }
}
