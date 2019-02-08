package vergecurrency.vergewallet.views.fragments.walletpageviews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.models.adapters.PriceStatisticsAdapter;
import vergecurrency.vergewallet.models.dataproc.PreferencesManager;
import vergecurrency.vergewallet.models.dataproc.PriceStatsDataReader;
import vergecurrency.vergewallet.structs.Currency;

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
		currencyCode = Currency.getCurrencyFromJson(pm.getSelectedCurrency()).getCurrency();


		if (rootView == null) {

			// Inflate the layout for this fragment
			rootView = inflater.inflate(R.layout.fragment_pageview_statistics, container, false);

			statisticsListView = (ListView) rootView.findViewById(R.id.statistics_listview);
			fillStatisticsListView(statisticsListView);

			pullRefreshView = (SwipeRefreshLayout) rootView.findViewById(R.id.statistics_refresh_pull);
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
