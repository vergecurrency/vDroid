package vergecurrency.vergewallet.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat

import vergecurrency.vergewallet.R

class PriceStatisticsAdapter(context: Context, entryList: List<Map.Entry<String, String>>) : ArrayAdapter<Map.Entry<String, String>>(context, R.layout.listview_item_stat, entryList) {

    //public List<Map.Entry<String,String>>


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        val vh: StatisticsItemViewHolder


        if (convertView == null) {

            vh = StatisticsItemViewHolder()

            val inflater = LayoutInflater.from(context)
            convertView = inflater.inflate(R.layout.listview_item_stat, parent, false)

            vh.statLabel = convertView!!.findViewById(R.id.listview_item_stat_label)
            vh.statValue = convertView.findViewById(R.id.listview_item_stat_value)

            convertView.tag = vh
        } else {
            vh = convertView.tag as StatisticsItemViewHolder
        }

        val currentEntry = this.getItem(position)

        formatView(vh, currentEntry!!)

        return convertView
    }

    private fun formatView(vh: StatisticsItemViewHolder, currentEntry: Map.Entry<String, String>) {


        val key = currentEntry.key
        val value = currentEntry.value

        when (key) {
            "24h Change %" -> if (value.contains("-"))
                vh.statValue!!.setTextColor(ContextCompat.getColor(context,R.color.material_green_500))
            else
                vh.statValue!!.setTextColor(ContextCompat.getColor(context,R.color.material_green_500))
            "24h High" -> vh.statValue!!.setTextColor(ContextCompat.getColor(context,R.color.material_green_500))
            "24h Low" -> vh.statValue!!.setTextColor(ContextCompat.getColor(context,R.color.material_green_500))
        }

        vh.statLabel!!.text = key
        vh.statValue!!.text = value
    }


    private class StatisticsItemViewHolder {
        internal var statLabel: TextView? = null
        internal var statValue: TextView? = null
    }
}
