package vergecurrency.vergewallet.view.ui.fragment.walletpageviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.view.adapter.TransactionsAdapter
import vergecurrency.vergewallet.viewmodel.TransactionsViewModel

class FragmentTransactionsPageView : Fragment() {

    object companion {
        fun newInstance(): FragmentTransactionsPageView {
            return FragmentTransactionsPageView()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        val mViewModel = ViewModelProviders.of(this).get(TransactionsViewModel::class.java)

        val rootView: View

        // Inflate the layout for this fragment
        if (mViewModel.balance <= 0)
            rootView = inflater.inflate(R.layout.fragment_notransactions, container, false)
        else {
            rootView = inflater.inflate(R.layout.fragment_pageview_transactions, container, false)
            val transactionList = rootView.findViewById<ListView>(R.id.transactions_listview)
            transactionList.adapter = TransactionsAdapter(this.context!!, mViewModel.transactionsList!!, false)
        }

        return rootView
    }


}
