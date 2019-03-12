package vergecurrency.vergewallet.view.ui.fragment.walletpageviews;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.view.adapter.PriceStatisticsAdapter;
import vergecurrency.vergewallet.service.model.PreferencesManager;
import vergecurrency.vergewallet.service.repository.PriceStatsDataReader;
import vergecurrency.vergewallet.service.model.Currency;

public class FragmentStatisticsPageView extends Fragment {

	PreferencesManager pm;
	String currencyCode;
	View rootView;
	ListView statisticsListView;
	SwipeRefreshLayout pullRefreshView;

	public FragmentStatisticsPageView() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


		pm = new PreferencesManager(this.getContext());
		currencyCode = Currency.getCurrencyFromJson(pm.getPreferredCurrency()).getCurrency();


		if (rootView == null) {

			// Inflate the layout for this fragment
			rootView = inflater.inflate(R.layout.fragment_pageview_statistics, container, false);

			statisticsListView = rootView.findViewById(R.id.statistics_listview);
			fillStatisticsListView(statisticsListView);

			pullRefreshView = rootView.findViewById(R.id.statistics_refresh_pull);
			pullRefreshView.setOnRefreshListener(pullRefreshListener());

		}

		return rootView;
	}

	void fillStatisticsListView(ListView lv) {

		Map<String, String> stats = PriceStatsDataReader.readPriceStatistics(currencyCode);

		ArrayList<Map.Entry<String, String>> statsList;
		statsList = new ArrayList<>(stats.entrySet());

		statisticsListView.setAdapter(new PriceStatisticsAdapter(this.getContext(), statsList));

	}

	private SwipeRefreshLayout.OnRefreshListener pullRefreshListener() {
		return () -> {
			fillStatisticsListView(statisticsListView);
			pullRefreshView.setRefreshing(false);
		};
	}


}
