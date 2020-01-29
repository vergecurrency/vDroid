package vergecurrency.vergewallet.view.ui.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SearchView

import androidx.lifecycle.ViewModelProviders

import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.service.model.TransactionFilterOption
import vergecurrency.vergewallet.view.adapter.TransactionsAdapter
import vergecurrency.vergewallet.view.base.BaseFragment
import vergecurrency.vergewallet.viewmodel.TransactionsViewModel

class FragmentTransactions : BaseFragment(), SearchView.OnQueryTextListener, RadioGroup.OnCheckedChangeListener {
    private var txa: TransactionsAdapter? = null
    private var option = TransactionFilterOption.ALL
    private var currentText = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        val mViewModel = ViewModelProviders.of(this).get(TransactionsViewModel::class.java)

        val rootView: View

        // Inflate the layout for this fragment
        if (mViewModel.balance == 0.0)
            rootView = inflater.inflate(R.layout.fragment_notransactions, container, false)
        else {
            rootView = inflater.inflate(R.layout.fragment_transactions, container, false)
            val transactionList = rootView.findViewById<ListView>(R.id.transactions_listview)
            transactionList.divider = null
            txa = TransactionsAdapter(context!!, mViewModel.transactionsList!!, true)
            transactionList.adapter = txa
            val transactionSearchView = rootView.findViewById<SearchView>(R.id.search)
            transactionSearchView.setOnQueryTextListener(this)
            val radioGroup = rootView.findViewById<RadioGroup>(R.id.transaction_radio_group)
            radioGroup.setOnCheckedChangeListener(this)
            setRadioTextBold(radioGroup);
        }

        return rootView
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        currentText = newText
        txa!!.filter(currentText, option)
        return false
    }

    override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {

        when (checkedId) {
            R.id.transactions_radio_all -> {
                option = TransactionFilterOption.ALL
                txa!!.filter(currentText, option)
                setRadioTextBold(group)
            }
            R.id.transactions_radio_send -> {
                option = TransactionFilterOption.SEND
                txa!!.filter(currentText, option)
                setRadioTextBold(group)
            }
            R.id.transactions_radio_receive -> {
                option = TransactionFilterOption.RECEIVE
                txa!!.filter(currentText, option)
                setRadioTextBold(group)
            }
        }
    }

    private fun setRadioTextBold(group: RadioGroup) {
        val radioAll: RadioButton = group.findViewById(R.id.transactions_radio_all)
        val radioSend: RadioButton = group.findViewById(R.id.transactions_radio_send)
        val radioReceive: RadioButton = group.findViewById(R.id.transactions_radio_receive)

        radioAll.setTypeface(null, Typeface.NORMAL);
        radioSend.setTypeface(null, Typeface.NORMAL);
        radioReceive.setTypeface(null, Typeface.NORMAL);

        if (radioReceive.isChecked) {
            radioReceive.setTypeface(null, Typeface.BOLD)
        }
        if (radioSend.isChecked) {
            radioSend.setTypeface(null, Typeface.BOLD)
        }
        if (radioAll.isChecked) {
            radioAll.setTypeface(null, Typeface.BOLD)
        }
    }
}
