package vergecurrency.vergewallet.helpers;

import android.graphics.Color;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.models.dataproc.GraphsDataReader;
import vergecurrency.vergewallet.structs.GraphInfo;

public final class GraphUtils {

	public static void createChart(CombinedChart cc) {
		GraphInfo gi = GraphsDataReader.readPriceStatistics();

		List<Entry> volumeData = createEntryList(gi.getVolume_usd().entrySet());
		List<Entry> priceData = createEntryList(gi.getPrice_usd().entrySet());

		cc.getDescription().setEnabled(false);
		cc.setBackgroundColor(Color.WHITE);
		cc.setDrawGridBackground(false);
		cc.setHighlightFullBarEnabled(false);


		//to play with, seems nice
		cc.setDrawBarShadow(false);

		cc.setDrawOrder(new CombinedChart.DrawOrder[]{CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE});


		Legend l = cc.getLegend();
		l.setWordWrapEnabled(true);
		l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
		l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
		l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
		l.setDrawInside(false);


		YAxis rightAxis = cc.getAxisRight();
		rightAxis.setDrawGridLines(false);
		rightAxis.setAxisMinimum(0f);
		rightAxis.setDrawLabels(false);

		YAxis leftAxis = cc.getAxisLeft();
		leftAxis.setDrawGridLines(false);
		leftAxis.setAxisMinimum(0f);
		leftAxis.setDrawLabels(false);


		XAxis xAxis = cc.getXAxis();
		xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
		xAxis.setAxisMinimum(0f);
		xAxis.setGranularity(1f);
		xAxis.setDrawGridLines(false);
		xAxis.setDrawLabels(false);


		//TODO : later
		CombinedData data = new CombinedData();
		data.setData(generateBarData(volumeData));
		data.setData(generateLineData(priceData));

		xAxis.setAxisMaximum(data.getXMax()+0.25f);

		cc.setData(data);
		cc.invalidate();


	}

	private static List<Entry> createEntryList(Set<Map.Entry<String, String>> set) {
		List<Entry> entries = new ArrayList<>();

		for (Map.Entry<String, String> e : set) {
			entries.add(new Entry(Float.parseFloat(e.getKey()), Float.parseFloat(e.getValue())));
		}

		return entries;
	}

	private static BarData generateBarData(List<Entry> data) {
		BarData barData = new BarData();
		ArrayList<BarEntry> barEntry = new ArrayList<>();

		for (Entry e :data) {
			barEntry.add(0, new BarEntry(e.getX(),e.getY()));
		}

		BarDataSet set = new BarDataSet(barEntry, "Bars");
		set.setAxisDependency(YAxis.AxisDependency.LEFT);

		barData.addDataSet(set);
		barData.setBarWidth(0.45f);

		return barData;
	}


	private static LineData generateLineData(List<Entry> data)  {
		LineData lineData = new LineData();

		LineDataSet set = new LineDataSet(data,"Line");
		set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
		set.setDrawValues(false);
		set.setDrawCircles(false);
		set.setDrawFilled(true);
		set.setDrawHorizontalHighlightIndicator(false);
		set.setLineWidth(1.5f);
		set.setHighlightLineWidth(1f);
		set.setFillAlpha(1);
		set.setHighLightColor(R.color.verge_colorPrimary);

		set.setAxisDependency(YAxis.AxisDependency.LEFT);
		lineData.addDataSet(set);

		return lineData;
	}
}
