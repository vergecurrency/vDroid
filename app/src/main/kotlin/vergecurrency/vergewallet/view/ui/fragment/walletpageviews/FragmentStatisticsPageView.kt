package vergecurrency.vergewallet.view.ui.fragment.walletpageviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.helpers.utils.NetworkUtils
import vergecurrency.vergewallet.view.adapter.PriceStatisticsAdapter
import vergecurrency.vergewallet.viewmodel.StatisticsViewModel

class FragmentStatisticsPageView : Fragment() {

    private var rootView: View? = null
    private var statisticsListView: ListView? = null
    private var pullRefreshView: SwipeRefreshLayout? = null
    private var mViewModel: StatisticsViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProviders.of(this).get(StatisticsViewModel::class.java)

        if (rootView == null) {

            // Inflate the layout for this fragment
            rootView = inflater.inflate(R.layout.fragment_pageview_statistics, container, false)

            initComponents()
        }

        return rootView
    }

    private fun initComponents() {

        statisticsListView = rootView!!.findViewById(R.id.statistics_listview)
        fillStatisticsListView(statisticsListView)

        pullRefreshView = rootView!!.findViewById(R.id.statistics_refresh_pull)
        pullRefreshView!!.setOnRefreshListener(pullRefreshListener())
    }


    private fun fillStatisticsListView(lv: ListView?) {
        if(NetworkUtils.checkNetworkState(this.context!!)) {
            lv!!.adapter = PriceStatisticsAdapter(this.context!!, mViewModel!!.statistics)
        }
    }

    private fun pullRefreshListener(): SwipeRefreshLayout.OnRefreshListener {
        return SwipeRefreshLayout.OnRefreshListener  {
            fillStatisticsListView(statisticsListView)
            pullRefreshView!!.isRefreshing = false
        }
    }


}
