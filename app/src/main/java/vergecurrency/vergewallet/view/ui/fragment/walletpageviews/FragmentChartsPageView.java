package vergecurrency.vergewallet.view.ui.fragment.walletpageviews;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.viewmodel.ChartsViewModel;

public class FragmentChartsPageView extends Fragment {

	private ChartsViewModel mViewModel;
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
		mViewModel = ViewModelProviders.of(this).get(ChartsViewModel.class);

		initComponents();

		createChart(6);

		return rootView;
	}

	private void initComponents() {
		combinedChart = rootView.findViewById(R.id.charts_card_chartview);

		grid = rootView.findViewById(R.id.charts_filter_grid_buttons);

		for (int i = 0; i < grid.getChildCount(); i++) {
			View child = grid.getChildAt(i);
			TextView tv2 = (TextView) child;
			tv2.setOnClickListener(onFilterClick(i + 1));
		}

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
			createChart(id);
		};
	}

	private void createChart(int filter) {

		CombinedData data = createCombinedData(filter);
		setChartProperties();
		setAxisPropertiesAndData(data);
		finalizeChart(data);
	}

	private CombinedData createCombinedData(int filter) {
		CombinedData data = new CombinedData();
		data.setData(generateBarData(mViewModel.getVolumeData(filter)));
		data.setData(generateLineData(mViewModel.getPriceData(filter)));
		return data;
	}

	private void setChartProperties() {
		combinedChart.setClickable(false);
		combinedChart.setBorderWidth(0f);
		combinedChart.setBorderColor(Color.WHITE);
		combinedChart.setBackgroundColor(Color.WHITE);
		combinedChart.getDescription().setEnabled(false);
		combinedChart.setDrawGridBackground(false);
		combinedChart.setHighlightFullBarEnabled(false);
		combinedChart.setDrawBarShadow(false);
		combinedChart.getLegend().setEnabled(false);
		//bars behind, line on foreground
		combinedChart.setDrawOrder(new CombinedChart.DrawOrder[]{CombinedChart.DrawOrder.LINE, CombinedChart.DrawOrder.BAR});
	}

	private void setAxisPropertiesAndData(CombinedData data) {
		YAxis rightAxis = combinedChart.getAxisRight();
		rightAxis.setDrawGridLines(false);
		rightAxis.setAxisMinimum(0f);
		rightAxis.setDrawLabels(false);

		YAxis leftAxis = combinedChart.getAxisLeft();
		leftAxis.setDrawGridLines(false);
		leftAxis.setAxisMinimum(0f);
		leftAxis.setDrawLabels(false);

		XAxis xAxis = combinedChart.getXAxis();
		xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
		xAxis.setAxisMinimum(0f);
		xAxis.setGranularity(1f);
		xAxis.setDrawGridLines(false);
		xAxis.setDrawLabels(false);
		xAxis.setValueFormatter((value, axis) -> {
			return new DecimalFormat("########0.0").format(value);
		});

		rightAxis.setAxisMaximum(data.getLineData().getYMax() * 1.1f);
		leftAxis.setAxisMaximum(data.getBarData().getYMax() * 1.1f);
		xAxis.setAxisMaximum(data.getXMax());
	}

	private void finalizeChart(CombinedData data) {
		combinedChart.setData(data);
		combinedChart.animateX(500);
		combinedChart.invalidate();
	}

	private BarData generateBarData(List<Entry> data) {
		BarData barData = new BarData();
		ArrayList<BarEntry> barEntry = new ArrayList<>();

		for (Entry e : data) {
			barEntry.add(0, new BarEntry(e.getX(), e.getY()));
		}

		BarDataSet set = new BarDataSet(barEntry, "Bars");
		set.setAxisDependency(YAxis.AxisDependency.LEFT);
		set.setHighLightColor(R.color.verge_colorPrimary);
		barData.addDataSet(set);
		barData.setBarWidth(0.2f);
		return barData;
	}


	private LineData generateLineData(List<Entry> data) {
		LineData lineData = new LineData();

		LineDataSet set = new LineDataSet(data, "Line");
		set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
		set.setDrawValues(false);
		set.setDrawCircles(false);
		set.setDrawFilled(true);
		set.setDrawHorizontalHighlightIndicator(false);
		set.setLineWidth(1.5f);
		set.setHighlightLineWidth(1f);
		set.setHighLightColor(R.color.verge_colorPrimary);
		set.setFillDrawable(ContextCompat.getDrawable(getContext(), R.drawable.fade_blue));
		set.setAxisDependency(YAxis.AxisDependency.RIGHT);
		lineData.addDataSet(set);

		return lineData;
	}


}
