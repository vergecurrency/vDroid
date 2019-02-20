package vergecurrency.vergewallet.views.fragments.walletpageviews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.helpers.GraphUtils;

public class FragmentChartsPageView extends Fragment {

	private View rootView;
	private CombinedChart combinedChart;
	private TextView oneDay;
	private TextView oneWeek;
	private TextView oneMonth;
	private TextView threeMonths;
	private TextView oneYear;
	private TextView allTime;
	private GridLayout grid;

	public FragmentChartsPageView() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		rootView = inflater.inflate(R.layout.fragment_pageview_charts, container, false);

		combinedChart = (CombinedChart) rootView.findViewById(R.id.charts_card_chartview);

		grid = (GridLayout) rootView.findViewById(R.id.charts_filter_grid_buttons);

		oneDay = rootView.findViewById(R.id.graph_filter_1d);
		oneWeek = rootView.findViewById(R.id.graph_filter_1w);
		oneMonth = rootView.findViewById(R.id.graph_filter_1m);
		threeMonths = rootView.findViewById(R.id.graph_filter_3m);
		oneYear = rootView.findViewById(R.id.graph_filter_1y);
		allTime = rootView.findViewById(R.id.graph_filter_all);

		oneDay.setOnClickListener(onFilterClick(1));
		oneWeek.setOnClickListener(onFilterClick(2));
		oneMonth.setOnClickListener(onFilterClick(3));
		threeMonths.setOnClickListener(onFilterClick(4));
		oneYear.setOnClickListener(onFilterClick(5));
		allTime.setOnClickListener(onFilterClick(6));

		GraphUtils.createChart(combinedChart, getContext(), 6);

		return rootView;
	}

	private View.OnClickListener onFilterClick(int id) {
		return v -> {
			TextView tv = (TextView) v;

			for (int i = 0; i < grid.getChildCount(); i++) {
				View child = grid.getChildAt(i);
				TextView tv2 = (TextView) child;
				tv2.setTextColor(getResources().getColor(R.color.verge_colorAccent));
			}

			tv.setTextColor(getResources().getColor(R.color.verge_colorPrimary));

			GraphUtils.createChart(combinedChart, getContext(), id);

		};
	}
}
