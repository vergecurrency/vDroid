package vergecurrency.vergewallet.view.ui.fragment.walletpageviews


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.helpers.utils.NetworkUtils
import vergecurrency.vergewallet.helpers.utils.UIUtils
import vergecurrency.vergewallet.viewmodel.ChartsViewModel
import java.text.DecimalFormat
import java.util.*

class FragmentChartsPageView : Fragment() {

    private var mViewModel: ChartsViewModel? = null
    private var rootView: View? = null
    private var combinedChart: CombinedChart? = null
    private var grid: GridLayout? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        rootView = inflater.inflate(R.layout.fragment_pageview_charts, container, false)
        mViewModel = ViewModelProviders.of(this).get(ChartsViewModel::class.java)

        initComponents()

        createChart(2)

        return rootView
    }

    private fun initComponents() {
        combinedChart = rootView!!.findViewById(R.id.charts_card_chartview)

        grid = rootView!!.findViewById(R.id.charts_filter_grid_buttons)

        for (i in 0 until grid!!.childCount) {
            val child = grid!!.getChildAt(i)
            val tv2 = child as TextView
            tv2.setOnClickListener(onFilterClick(i + 1))
        }

    }


    private fun onFilterClick(id: Int): View.OnClickListener {
        return View.OnClickListener {
            val tv = it as TextView

            for (i in 0 until grid!!.childCount) {

                val child = grid!!.getChildAt(i)
                val tv2 = child as TextView
                tv2.setTextColor(getColor(R.attr.vg_secondaryLight))
            }

            tv.setTextColor(getColor(R.attr.vg_primaryLight))
            createChart(id)
        }
    }

    private fun createChart(filter: Int) {
        if (NetworkUtils.checkNetworkState(context!!)) {
            val data = createCombinedData(filter)
            setChartProperties()
            setAxisPropertiesAndData(data)
            finalizeChart(data)
        }
    }

    private fun createCombinedData(filter: Int): CombinedData {
        val data = CombinedData()
        mViewModel!!.retrieveData(filter)
        data.setData(generateBarData(mViewModel!!.getVolumeData()))
        data.setData(generateLineData(mViewModel!!.getPriceData()))
        return data
    }

    private fun setChartProperties() {
        combinedChart!!.isClickable = false
        combinedChart!!.setBorderWidth(0f)
        combinedChart!!.setBorderColor(getColor(R.attr.vg_backgroundWhite))
        combinedChart!!.setBackgroundColor(getColor(R.attr.vg_backgroundWhite))
        combinedChart!!.description.isEnabled = false
        combinedChart!!.setDrawGridBackground(false)
        combinedChart!!.isHighlightFullBarEnabled = false
        combinedChart!!.setDrawBarShadow(false)
        combinedChart!!.legend.isEnabled = false
        //bars behind, line on foreground
        combinedChart!!.drawOrder = arrayOf(CombinedChart.DrawOrder.LINE, CombinedChart.DrawOrder.BAR)
    }

    private fun setAxisPropertiesAndData(data: CombinedData) {
        val rightAxis = combinedChart!!.axisRight
        rightAxis.setDrawGridLines(false)
        rightAxis.axisMinimum = 0f
        rightAxis.setDrawLabels(false)

        val leftAxis = combinedChart!!.axisLeft
        leftAxis.setDrawGridLines(false)
        leftAxis.axisMinimum = 0f
        leftAxis.setDrawLabels(false)

        val xAxis = combinedChart!!.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTH_SIDED
        xAxis.axisMinimum = 0f
        xAxis.granularity = 1f
        xAxis.setDrawGridLines(false)
        xAxis.setDrawLabels(false)
        xAxis.setValueFormatter(vf)

        rightAxis.axisMaximum = data.lineData.yMax * 1.1f
        leftAxis.axisMaximum = data.barData.yMax * 1.1f
        xAxis.axisMaximum = data.xMax
    }

    private fun finalizeChart(data: CombinedData) {
        combinedChart!!.data = data
        combinedChart!!.animateX(500)
        combinedChart!!.invalidate()
    }

    private fun generateBarData(data: List<Entry>): BarData {
        val barData = BarData()
        val barEntry = ArrayList<BarEntry>()

        for (e in data) {
            barEntry.add(0, BarEntry(e.x, e.y))
        }

        val set = BarDataSet(barEntry, "Bars")
        set.axisDependency = YAxis.AxisDependency.LEFT
        set.highLightColor = getColor(R.attr.vg_primaryLight)
        barData.addDataSet(set)
        barData.barWidth = 0.2f
        return barData
    }


    private fun generateLineData(data: List<Entry>): LineData {
        val lineData = LineData()

        val set = LineDataSet(data, "Line")
        set.mode = LineDataSet.Mode.CUBIC_BEZIER
        set.setDrawValues(false)
        set.setDrawCircles(false)
        set.setDrawFilled(true)
        set.setDrawHorizontalHighlightIndicator(false)
        set.lineWidth = 1.5f
        set.highlightLineWidth = 1f
        set.highLightColor = getColor(R.attr.vg_primaryLight)
        set.fillDrawable = ContextCompat.getDrawable(context!!, R.drawable.fade_blue)
        set.axisDependency = YAxis.AxisDependency.RIGHT
        lineData.addDataSet(set)

        return lineData
    }

    private fun getColor(attr: Int): Int {
        return ContextCompat.getColor(context!!, UIUtils.resolveAttr(attr, context!!))
    }

    var vf = object : ValueFormatter() {

        var df = DecimalFormat("########0.0")

        override fun getFormattedValue(value: Float, axis: AxisBase?): String {
            return df.format(value)
        }
    }

}
