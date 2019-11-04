package vergecurrency.vergewallet.view.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView

import androidx.lifecycle.ViewModelProviders

import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.view.base.BaseFragment
import vergecurrency.vergewallet.viewmodel.SendFragmentViewModel

@SuppressLint("ValidFragment")
class FragmentSend : BaseFragment {

    private var requestedAddress: String? = null
    private var amountTextView: TextView? = null
    private var amount: EditText? = null
    private var address: EditText? = null
    //External inputs
    private var requestedAmount: Double = 0.0

    constructor()

    @SuppressLint("ValidFragment")
    constructor(vararg amount: Double) {
        requestedAmount = amount[0]
    }

    @SuppressLint("ValidFragment")
    constructor(vararg address: String) {
        requestedAddress = address[0]
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val mViewModel = ViewModelProviders.of(this).get(SendFragmentViewModel::class.java)

        // Inflate the layout for this fragment

        val rootView: View
        if (mViewModel.balance!! <= 0) {
            rootView = inflater.inflate(R.layout.fragment_send_nobalance, container, false)
        } else {
            rootView = inflater.inflate(R.layout.fragment_send_balance, container, false)
            amountTextView = rootView.findViewById(R.id.transaction_total_amount)
            address = rootView.findViewById(R.id.send_balance_address)
            amount = rootView.findViewById(R.id.amount)
            amount!!.addTextChangedListener(amountTW())

            if (requestedAmount != 0.0) {
                setPreRequestedAmount()
            }
            if (requestedAddress != null) {
                setPreRequestedAddress()
            }
        }

        return rootView
    }

    private fun amountTW(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                amountTextView!!.text = s
            }

            override fun afterTextChanged(s: Editable) {

            }
        }
    }

    private fun setPreRequestedAmount() {
        amount!!.setText(String.format("%s", requestedAmount))
    }

    private fun setPreRequestedAddress() {
        address!!.setText(requestedAddress)
    }
}
