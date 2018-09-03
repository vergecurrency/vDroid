package vergecurrency.vergewallet.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import vergecurrency.vergewallet.R;

public class FragmentSend extends Fragment {
	
	
	TextView amountTextView;
	EditText amount;
	double balance = 10d;
	
	
	public FragmentSend() {
		// Required empty public constructor
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View rootView;
		if (balance <= 0) {
			rootView = inflater.inflate(R.layout.fragment_send_nobalance, container, false);
		} else {
			rootView = inflater.inflate(R.layout.fragment_send_balance, container, false);
			amountTextView = rootView.findViewById(R.id.transaction_total_amount);
			amount = rootView.findViewById(R.id.amount);
			amount.addTextChangedListener(amountTW());
		}
		
		return rootView;
	}

	private TextWatcher amountTW() {
		return new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			amountTextView.setText(s);
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			
			}
		};
	}
}
