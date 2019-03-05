package vergecurrency.vergewallet.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import vergecurrency.vergewallet.R;

public class PriceStatisticsAdapter extends ArrayAdapter<Map.Entry<String, String>> {


	public PriceStatisticsAdapter(Context context, List<Map.Entry<String, String>> entryList) {
		super(context, R.layout.listview_item_stat, entryList);
	}

	//public List<Map.Entry<String,String>>


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		StatisticsItemViewHolder vh;


		if (convertView == null) {

			vh = new StatisticsItemViewHolder();

			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.listview_item_stat, parent, false);

			vh.statLabel = convertView.findViewById(R.id.listview_item_stat_label);
			vh.statValue = convertView.findViewById(R.id.listview_item_stat_value);

			convertView.setTag(vh);
		} else {
			vh = (StatisticsItemViewHolder) convertView.getTag();
		}

		Map.Entry<String, String> currentEntry = this.getItem(position);

		formatView(vh,currentEntry);

		return convertView;
	}

	private void formatView(StatisticsItemViewHolder vh, Map.Entry<String, String> currentEntry) {


		String key = currentEntry.getKey();
		String value = currentEntry.getValue();

		if (key == "24h Change %") {
			if (value.contains("-"))
				vh.statValue.setTextColor(getContext().getResources().getColor(R.color.material_red_500));
			else
				vh.statValue.setTextColor(getContext().getResources().getColor(R.color.material_green_500));

		} else if (key == "24h High") {
			vh.statValue.setTextColor(getContext().getResources().getColor(R.color.material_green_500));
		} else if (key == "24h Low") {
			vh.statValue.setTextColor(getContext().getResources().getColor(R.color.material_red_500));
		}

		vh.statLabel.setText(key);
		vh.statValue.setText(value);
	}


	private static class StatisticsItemViewHolder {
		TextView statLabel;
		TextView statValue;
	}
}
