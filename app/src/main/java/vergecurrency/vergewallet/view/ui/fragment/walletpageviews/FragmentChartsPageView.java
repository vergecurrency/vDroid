package vergecurrency.vergewallet.view.ui.fragment.walletpageviews;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.utilities.GraphUtils;
import vergecurrency.vergewallet.viewmodel.ChartsPageViewModel;

public class FragmentChartsPageView extends Fragment {

	private ViewModel mViewModel;
	private View rootView;
	private CombinedChart combinedChart;
	private GridLayout grid;

	public FragmentChartsPageView() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		rootView = inflater.inflate(R.layout.fragment_pageview_charts, container, false);

		mViewModel = ViewModelProviders.of(this).get(ChartsPageViewModel.class);

		combinedChart = rootView.findViewById(R.id.charts_card_chartview);

		grid = rootView.findViewById(R.id.charts_filter_grid_buttons);

		for (int i = 0; i < grid.getChildCount(); i++) {
			View child = grid.getChildAt(i);
			TextView tv2 = (TextView) child;
			tv2.setOnClickListener(onFilterClick(i + 1));
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
