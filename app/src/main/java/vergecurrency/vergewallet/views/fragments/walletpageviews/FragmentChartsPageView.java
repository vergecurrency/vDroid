package vergecurrency.vergewallet.views.fragments.walletpageviews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.CombinedChart;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.helpers.GraphUtils;

public class FragmentChartsPageView extends Fragment {

	private View rootView;
	private CombinedChart combinedChart;


	public FragmentChartsPageView() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		rootView = inflater.inflate(R.layout.fragment_pageview_charts, container, false);


		combinedChart = (CombinedChart) rootView.findViewById(R.id.charts_card_chartview);

		//TODO : Move all this shit to GraphUtils. RIGHT AFTER YOU'RE DONE TESTING MANUEL
		GraphUtils.createChart(combinedChart, getContext());

		return rootView;
	}


}
