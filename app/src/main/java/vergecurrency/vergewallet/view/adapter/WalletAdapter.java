package vergecurrency.vergewallet.view.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import vergecurrency.vergewallet.view.ui.fragment.walletpageviews.FragmentChartsPageView;
import vergecurrency.vergewallet.view.ui.fragment.walletpageviews.FragmentStatisticsPageView;
import vergecurrency.vergewallet.view.ui.fragment.walletpageviews.FragmentTransactionsPageView;

public class WalletAdapter extends FragmentPagerAdapter {

	private Context context;

	public WalletAdapter(FragmentManager fm, Context c) {
		super(fm);
		this.context = c;
	}

	@Override
	public Fragment getItem(int pos) {

		switch (pos) {

			case 0:
				return FragmentTransactionsPageView.instantiate(context, FragmentTransactionsPageView.class.getName());
			case 1:
				return FragmentStatisticsPageView.instantiate(context, FragmentStatisticsPageView.class.getName());
			case 2:
				return FragmentChartsPageView.instantiate(context, FragmentChartsPageView.class.getName());
			//case 3:
			//	return FragmentTwitterPageView.instantiate(context, FragmentTwitterPageView.class.getName());
			default:
				return null;
		}
	}

	@Override
	public int getCount() {
		return 3;
		//return 4;
	}
}
