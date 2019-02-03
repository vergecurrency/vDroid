package vergecurrency.vergewallet.models.adapters;

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

		vh.statLabel.setText(currentEntry.getKey());
		vh.statValue.setText(currentEntry.getValue());

		return convertView;
	}

	private static class StatisticsItemViewHolder {
		TextView statLabel;
		TextView statValue;
	}
}
