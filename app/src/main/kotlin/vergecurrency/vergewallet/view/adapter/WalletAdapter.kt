package vergecurrency.vergewallet.view.adapter

import android.content.Context

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import vergecurrency.vergewallet.view.ui.fragment.walletpageviews.FragmentChartsPageView
import vergecurrency.vergewallet.view.ui.fragment.walletpageviews.FragmentStatisticsPageView
import vergecurrency.vergewallet.view.ui.fragment.walletpageviews.FragmentTransactionsPageView

class WalletAdapter(fm: FragmentManager, private val context: Context) : FragmentPagerAdapter(fm) {

    override fun getItem(pos: Int): Fragment {

        when (pos) {
            0 -> return return Fragment.instantiate(context, FragmentTransactionsPageView::class.java.name)
            1 -> return Fragment.instantiate(context, FragmentStatisticsPageView::class.java.name)
            2 -> return Fragment.instantiate(context, FragmentChartsPageView::class.java.name)
            else -> return Fragment.instantiate(context, FragmentChartsPageView::class.java.name)
        }
    }

    override fun getCount(): Int {
        return 3

    }
}
