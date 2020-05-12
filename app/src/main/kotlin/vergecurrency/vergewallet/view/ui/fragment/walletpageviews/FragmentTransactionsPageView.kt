package vergecurrency.vergewallet.view.ui.fragment.walletpageviews

import RecycleTouchListener
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.helpers.utils.TransactionUtils
import vergecurrency.vergewallet.service.model.Transaction
import vergecurrency.vergewallet.view.adapter.TransactionRecycleAdapter
import vergecurrency.vergewallet.view.ui.activity.TransactionDetailActivity
import vergecurrency.vergewallet.viewmodel.TransactionsViewModel
import java.util.*
import java.util.EnumSet.of

class FragmentTransactionsPageView : Fragment() {

    object companion {
        fun newInstance(): FragmentTransactionsPageView {
            return FragmentTransactionsPageView()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        val mViewModel = ViewModelProvider(this).get(TransactionsViewModel::class.java)

        val rootView: View

        // Inflate the layout for this fragment
        if (mViewModel.balance <= 0)
            rootView = inflater.inflate(R.layout.fragment_notransactions, container, false)
        else {
            rootView = inflater.inflate(R.layout.fragment_pageview_transactions, container, false)

            val recycleViewTransactions = rootView.findViewById(R.id.rv_transactions_page_view) as RecyclerView
            recycleViewTransactions.adapter = TransactionRecycleAdapter(mViewModel.transactionsList!!, false)
            recycleViewTransactions.layoutManager = LinearLayoutManager(rootView.context)
            recycleViewTransactions.addOnItemTouchListener(RecycleTouchListener(context, recycleViewTransactions, object : RecycleTouchListener.ClickListener {
                override fun onClick(view: View?, position: Int, tx : Transaction) {
                    val intent = Intent(context, TransactionDetailActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    intent.putExtra(TransactionUtils.EXTRA_TRANSACTION, tx)
                    context?.startActivity(intent)
                }

                override fun onLongClick(view: View?, position: Int, tx: Transaction) {
                }
            }))

        }

        return rootView
    }


}
