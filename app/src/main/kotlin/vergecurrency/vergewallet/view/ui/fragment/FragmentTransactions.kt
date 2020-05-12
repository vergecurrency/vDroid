package vergecurrency.vergewallet.view.ui.fragment

import RecycleTouchListener
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Movie
import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.helpers.utils.TransactionUtils
import vergecurrency.vergewallet.service.model.Transaction
import vergecurrency.vergewallet.service.model.TransactionFilterOption
import vergecurrency.vergewallet.view.adapter.TransactionRecycleAdapter
import vergecurrency.vergewallet.view.base.BaseFragment
import vergecurrency.vergewallet.view.ui.activity.TransactionDetailActivity
import vergecurrency.vergewallet.viewmodel.TransactionsViewModel
import java.util.*


class FragmentTransactions : BaseFragment(), SearchView.OnQueryTextListener, RadioGroup.OnCheckedChangeListener {
    private var txa: TransactionRecycleAdapter? = null
    private var option = TransactionFilterOption.ALL
    private var currentText = ""
    private lateinit var titleTextView: TextView;
    private lateinit var titleText: String
    private lateinit var titleTextSingular: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        val mViewModel = ViewModelProvider(this).get(TransactionsViewModel::class.java)

        val rootView: View

        // Inflate the layout for this fragment
        if (mViewModel.balance == 0.0)
            rootView = inflater.inflate(R.layout.fragment_notransactions, container, false)
        else {
            rootView = inflater.inflate(R.layout.fragment_transactions, container, false)
            titleTextView = rootView.findViewById(R.id.wallet_transactions_title);
            titleText = rootView.resources.getString(R.string.transaction_title)
            titleTextSingular = rootView.resources.getString(R.string.transaction_title_singular)

            //initialize RecycleView
            val recycleViewTransactions = rootView.findViewById(R.id.rv_transactions_fragment) as RecyclerView
            val transactions = mViewModel.transactionsList!!;
            Collections.sort(transactions, Transaction.Companion.TimeComparatorDESC);
            txa = TransactionRecycleAdapter(transactions, true)
            recycleViewTransactions.adapter = txa
            recycleViewTransactions.layoutManager = LinearLayoutManager(rootView.context)

            recycleViewTransactions.addOnItemTouchListener(RecycleTouchListener(context, recycleViewTransactions, object : RecycleTouchListener.ClickListener {
                override fun onClick(view: View?, position: Int, tx: Transaction) {
                    val intent = Intent(context, TransactionDetailActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    intent.putExtra(TransactionUtils.EXTRA_TRANSACTION, tx)
                    context?.startActivity(intent)
                }

                override fun onLongClick(view: View?, position: Int, tx: Transaction) {
                    TODO("Not yet implemented")
                }
            }))

            //filters
            val transactionSearchView = rootView.findViewById<SearchView>(R.id.search)
            transactionSearchView.setOnQueryTextListener(this)
            val radioGroup = rootView.findViewById<RadioGroup>(R.id.transaction_radio_group)
            radioGroup.setOnCheckedChangeListener(this)
            styleRadio(radioGroup);
            setTitle(txa!!.getTransactionCount());
        }

        return rootView
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        currentText = newText
        txa!!.filter(currentText, option)
        setTitle(txa!!.getTransactionCount());
        return false
    }

    override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {

        when (checkedId) {
            R.id.transactions_radio_all -> {
                option = TransactionFilterOption.ALL
                txa!!.filter(currentText, option)
                styleRadio(group)
                setTitle(txa!!.getTransactionCount());
            }
            R.id.transactions_radio_send -> {
                option = TransactionFilterOption.SEND
                txa!!.filter(currentText, option)
                styleRadio(group)
                setTitle(txa!!.getTransactionCount());
            }
            R.id.transactions_radio_receive -> {
                option = TransactionFilterOption.RECEIVE
                txa!!.filter(currentText, option)
                styleRadio(group)
                setTitle(txa!!.getTransactionCount());
            }
        }
    }

    private fun setTitle(count: Int) {
        if (count == 1) {
            titleTextView.text = "$count $titleTextSingular";
        } else {
            titleTextView.text = "$count $titleText";
        }
    }

    private fun setRadioCheckedColor(context: Context, radio: RadioButton) {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(R.attr.vg_radio_checked_text_color, typedValue, true)
        radio.setTextColor(typedValue.data)
    }

    private fun styleRadio(group: RadioGroup) {
        val radioAll: RadioButton = group.findViewById(R.id.transactions_radio_all)
        val radioSend: RadioButton = group.findViewById(R.id.transactions_radio_send)
        val radioReceive: RadioButton = group.findViewById(R.id.transactions_radio_receive)

        radioAll.setTypeface(null, Typeface.NORMAL)
        radioSend.setTypeface(null, Typeface.NORMAL)
        radioReceive.setTypeface(null, Typeface.NORMAL)
        radioAll.setTextColor(Color.BLACK)
        radioSend.setTextColor(Color.BLACK)
        radioReceive.setTextColor(Color.BLACK)

        if (radioReceive.isChecked) {
            radioReceive.setTypeface(null, Typeface.BOLD)
            setRadioCheckedColor(group.context, radioReceive)
        }
        if (radioSend.isChecked) {
            radioSend.setTypeface(null, Typeface.BOLD)
            setRadioCheckedColor(group.context, radioSend)
        }
        if (radioAll.isChecked) {
            radioAll.setTypeface(null, Typeface.BOLD)
            setRadioCheckedColor(group.context, radioAll)
        }
    }
}
