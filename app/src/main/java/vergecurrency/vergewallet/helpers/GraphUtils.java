package vergecurrency.vergewallet.helpers;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

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
import java.util.Map;
import java.util.Set;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.models.dataproc.GraphsDataReader;
import vergecurrency.vergewallet.structs.GraphInfo;

public final class GraphUtils {


	public static void createChart(CombinedChart cc, Context context,int filter) {

		GraphInfo gi = GraphsDataReader.readPriceStatistics(filter);

		List<Entry> volumeData = createEntryList(gi.getVolume_usd().entrySet());
		List<Entry> priceData = createEntryList(gi.getPrice_usd().entrySet());


		cc.setClickable(false);
		cc.setBorderWidth(0f);
		cc.getDescription().setEnabled(false);
		cc.setBackgroundColor(Color.WHITE);
		cc.setDrawGridBackground(false);
		cc.setHighlightFullBarEnabled(false);
		cc.setDrawBarShadow(false);

		//bars behind, line on foreground
		cc.setDrawOrder(new CombinedChart.DrawOrder[]{CombinedChart.DrawOrder.LINE, CombinedChart.DrawOrder.BAR});

		cc.getLegend().setEnabled(false);

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
		xAxis.setValueFormatter((value, axis) -> {
			return new DecimalFormat("########0.0").format(value);
		});


		CombinedData data = new CombinedData();
		data.setData(generateBarData(volumeData));
		data.setData(generateLineData(priceData, context));

		rightAxis.setAxisMaximum(data.getLineData().getYMax() * 1.1f);
		leftAxis.setAxisMaximum(data.getBarData().getYMax() * 1.1f);
		xAxis.setAxisMaximum(data.getXMax());

		cc.setData(data);
		cc.animateX(500);

		//cc.invalidate();


	}

	private static List<Entry> createEntryList(Set<Map.Entry<String, String>> set) {
		List<Entry> entries = new ArrayList<>();
		float counter = 0;
		for (Map.Entry<String, String> e : set) {
			entries.add(new Entry(counter, Float.parseFloat(e.getValue())));
			counter++;
		}

		return entries;
	}

	private static BarData generateBarData(List<Entry> data) {
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


	private static LineData generateLineData(List<Entry> data, Context c) {
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
		set.setFillDrawable(ContextCompat.getDrawable(c, R.drawable.fade_blue));
		set.setAxisDependency(YAxis.AxisDependency.RIGHT);
		lineData.addDataSet(set);

		return lineData;
	}

}
