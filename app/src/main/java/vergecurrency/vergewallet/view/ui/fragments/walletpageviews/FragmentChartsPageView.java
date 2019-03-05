package vergecurrency.vergewallet.view.ui.fragments.walletpageviews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.utilities.GraphUtils;

public class FragmentChartsPageView extends Fragment {

	private View rootView;
	private CombinedChart combinedChart;
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

		for (int i = 0; i < grid.getChildCount(); i++) {
			View child = grid.getChildAt(i);
			TextView tv2 = (TextView) child;
			tv2.setOnClickListener(onFilterClick(i+1));
		}

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
