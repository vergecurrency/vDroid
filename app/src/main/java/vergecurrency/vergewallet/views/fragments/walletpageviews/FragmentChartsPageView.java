package vergecurrency.vergewallet.views.fragments.walletpageviews;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.models.dataproc.GraphsDataReader;
import vergecurrency.vergewallet.structs.GraphInfo;

import static com.github.mikephil.charting.charts.CombinedChart.DrawOrder;

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
		createChart(combinedChart);

		return rootView;
	}

	void createChart(CombinedChart cc) {
		GraphInfo gi = GraphsDataReader.readPriceStatistics();

		List<Entry> volumeData = fillGraphData(gi.getVolume_usd().entrySet());
		List<Entry> priceData = fillGraphData(gi.getPrice_usd().entrySet());

		cc.getDescription().setEnabled(false);
		cc.setBackgroundColor(Color.WHITE);
		cc.setDrawGridBackground(false);
		cc.setHighlightFullBarEnabled(false);

		//to play with, seems nice
		cc.setDrawBarShadow(false);

		cc.setDrawOrder(new DrawOrder[]{DrawOrder.BAR, DrawOrder.LINE});

		YAxis rightAxis = cc.getAxisRight();
		rightAxis.setDrawGridLines(false);
		rightAxis.setAxisMinimum(0f);

		YAxis leftAxis = cc.getAxisLeft();
		leftAxis.setDrawGridLines(false);
		leftAxis.setAxisMinimum(0f);

		XAxis xAxis = cc.getXAxis();
		xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
		xAxis.setAxisMinimum(0f);
		xAxis.setGranularity(1f);
		//TODO : later
		//xAxis.setValueFormatter( new ValueFormatter() {}

		CombinedData data = new CombinedData();


	}

	private List<Entry> fillGraphData(Set<Map.Entry<String, String>> set) {
		List<Entry> entries = new ArrayList<>();

		for (Map.Entry<String, String> e : set) {
			entries.add(new Entry(Float.parseFloat(e.getKey()), Float.parseFloat(e.getValue())));
		}

		return entries;
	}

	private BarData generateBarData() {
		return null;
	}

	private LineData generateLineData()  {
		return null;
	}


}
