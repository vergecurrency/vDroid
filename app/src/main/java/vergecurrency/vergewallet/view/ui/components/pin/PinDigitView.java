package vergecurrency.vergewallet.view.ui.components.pin;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import vergecurrency.vergewallet.R;


public class PinDigitView extends LinearLayout {
	private TextView mTextViewDigit;
	private TextView mTextViewLetters;


	public PinDigitView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.compound_pin_digit,this,true);

		mTextViewDigit = (TextView)getChildAt(0);
		mTextViewLetters = (TextView)getChildAt(1);
	}
}
