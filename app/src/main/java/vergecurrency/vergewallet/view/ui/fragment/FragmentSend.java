package vergecurrency.vergewallet.view.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import androidx.fragment.app.Fragment;
import vergecurrency.vergewallet.R;

@SuppressLint("ValidFragment")
public class FragmentSend extends Fragment {


	View rootView;
	private TextView amountTextView;
	private EditText amount;
	private EditText address;
	private ImageView qrCodeIcon;

	//External inputs
	private double requestedAmount;
	private String requestedAddress;


	public FragmentSend() {

	}

	@SuppressLint("ValidFragment")
	public FragmentSend(double... amount) {
		requestedAmount = amount[0];
	}

	@SuppressLint("ValidFragment")
	public FragmentSend(String... address) {
		requestedAddress = address[0];
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment

		//mock balance
		double balance = 10d;


		if (balance <= 0) {
			rootView = inflater.inflate(R.layout.fragment_send_nobalance, container, false);
		} else {
			rootView = inflater.inflate(R.layout.fragment_send_balance, container, false);
			initComponents();
		}

		return rootView;
	}

	private void initComponents() {

		amountTextView = rootView.findViewById(R.id.transaction_total_amount);
		address = rootView.findViewById(R.id.send_balance_address);

		qrCodeIcon = rootView.findViewById(R.id.send_balance_qr_icon);
		qrCodeIcon.setOnClickListener(onQRCodeIconClickListener());

		amount = rootView.findViewById(R.id.amount);
		amount.addTextChangedListener(amountTW());



		if (requestedAmount != 0) {
			setPreRequestedAmount();
		}
		if (requestedAddress != null) {
			setPreRequestedAddress();
		}
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

	private View.OnClickListener onQRCodeIconClickListener() {
		return v-> {
			/*BarcodeDetector detector =
					new BarcodeDetector.Builder(getContext())
							.setBarcodeFormats(Barcode.QR_CODE)
							.build();
			if(!detector.isOperational()){
				Toast.makeText(getContext(),"Could not setup", Toast.LENGTH_SHORT).show();
				//return;
			}*/

			//Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
			//SparseArray<Barcode> barcodes = detector.detect(frame);
			//Barcode thisCode = barcodes.valueAt(0);
			//TextView txtView = (TextView) findViewById(R.id.txtContent);
			//txtView.setText(thisCode.rawValue);
		};
	}


	public void setPreRequestedAmount() {
		amount.setText("" + requestedAmount);
	}

	public void setPreRequestedAddress() {
		address.setText(requestedAddress);
	}
}
