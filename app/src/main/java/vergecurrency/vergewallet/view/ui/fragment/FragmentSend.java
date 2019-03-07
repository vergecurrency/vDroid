package vergecurrency.vergewallet.view.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import vergecurrency.vergewallet.R;

@SuppressLint("ValidFragment")
public class FragmentSend extends Fragment {


    TextView amountTextView;

    //mock balance
    double balance = 10d;

    EditText amount;
    EditText address;
    PopupWindow popup;
    View rootView;
    //External inputs
    double requestedAmount;
    String requestedAddress;


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

        if (balance <= 0) {
            rootView = inflater.inflate(R.layout.fragment_send_nobalance, container, false);
        } else {
            rootView = inflater.inflate(R.layout.fragment_send_balance, container, false);
            amountTextView = rootView.findViewById(R.id.transaction_total_amount);
            address = rootView.findViewById(R.id.send_balance_address);
            amount = rootView.findViewById(R.id.amount);
            amount.addTextChangedListener(amountTW());

            if (requestedAmount != 0) {
                setPreRequestedAmount();
            }
            if (requestedAddress != null) {
                setPreRequestedAddress();
            }
        }

        popup = new PopupWindow(this.getContext());

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

    public void setPreRequestedAmount() {
        amount.setText(""+requestedAmount);
    }

    public void setPreRequestedAddress() {
        address.setText(requestedAddress);
    }
}
