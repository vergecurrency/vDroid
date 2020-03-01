package vergecurrency.vergewallet.view.adapter

import android.content.Context

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import vergecurrency.vergewallet.view.ui.fragment.walletpageviews.FragmentChartsPageView
import vergecurrency.vergewallet.view.ui.fragment.walletpageviews.FragmentStatisticsPageView
import vergecurrency.vergewallet.view.ui.fragment.walletpageviews.FragmentTransactionsPageView

class WalletAdapter(fm: FragmentManager, private val context: Context) : FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(pos: Int): Fragment {

        when (pos) {
            0 -> return FragmentFactory.loadFragmentClass(context.classLoader, FragmentTransactionsPageView::class.java.name).newInstance()
            1 -> return FragmentFactory.loadFragmentClass(context.classLoader, FragmentStatisticsPageView::class.java.name).newInstance()
            2 -> return FragmentFactory.loadFragmentClass(context.classLoader, FragmentChartsPageView::class.java.name).newInstance()
            else -> return FragmentFactory.loadFragmentClass(context.classLoader, FragmentChartsPageView::class.java.name).newInstance()
        }
    }

    override fun getCount(): Int {
        return 3

    }
}
