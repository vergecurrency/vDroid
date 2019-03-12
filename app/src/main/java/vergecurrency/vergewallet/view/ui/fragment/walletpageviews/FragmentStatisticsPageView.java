package vergecurrency.vergewallet.view.ui.fragment.walletpageviews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.view.adapter.PriceStatisticsAdapter;
import vergecurrency.vergewallet.viewmodel.StatisticsViewModel;

public class FragmentStatisticsPageView extends Fragment {

	private View rootView;
	private ListView statisticsListView;
	private SwipeRefreshLayout pullRefreshView;
	private StatisticsViewModel mViewModel;

	public FragmentStatisticsPageView() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mViewModel = ViewModelProviders.of(this).get(StatisticsViewModel.class);

		if (rootView == null) {

			// Inflate the layout for this fragment
			rootView = inflater.inflate(R.layout.fragment_pageview_statistics, container, false);

			initComponents();
		}

		return rootView;
	}

	private void initComponents() {

		statisticsListView = rootView.findViewById(R.id.statistics_listview);
		fillStatisticsListView(statisticsListView);

		pullRefreshView = rootView.findViewById(R.id.statistics_refresh_pull);
		pullRefreshView.setOnRefreshListener(pullRefreshListener());
	}


	private void fillStatisticsListView(ListView lv) {
		statisticsListView.setAdapter(new PriceStatisticsAdapter(this.getContext(), mViewModel.getStatistics()));
	}

	private SwipeRefreshLayout.OnRefreshListener pullRefreshListener() {
		return () -> {
			fillStatisticsListView(statisticsListView);
			pullRefreshView.setRefreshing(false);
		};
	}


}
