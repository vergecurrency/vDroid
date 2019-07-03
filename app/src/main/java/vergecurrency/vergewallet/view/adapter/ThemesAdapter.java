package vergecurrency.vergewallet.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.helpers.utils.UIUtils;
import vergecurrency.vergewallet.service.model.PreferencesManager;

public class ThemesAdapter extends ArrayAdapter<String> implements View.OnClickListener {

	/**
	 * Transaction adapter constructor
	 *
	 * @param context the application context
	 * @param themes  the themes list we need to display
	 */
	public ThemesAdapter(@NonNull Context context, ArrayList<String> themes) {
		super(context, R.layout.listview_item_theme, themes);
	}

	@Override
	public void onClick(View v) {
		int position = (Integer) v.getTag();
		String theme = getItem(position);

		if (v.getId() == R.id.listview_theme_item) {
			Toast.makeText(v.getContext(), "Theme switched to " + theme, Toast.LENGTH_SHORT).show();
			PreferencesManager.setCurrentTheme(theme);

			UIUtils.setTheme(theme, getContext(), true);
			UIUtils.restartApplication(getContext());
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		String theme = getItem(position);
		ThemeItemViewHolder vh;

		if (convertView == null) {
			vh = new ThemeItemViewHolder();
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.listview_item_theme, parent, false);
			vh.themeName = convertView.findViewById(R.id.listview_theme_name);
			vh.themeId = convertView.findViewById(R.id.listview_theme_item);

			convertView.setTag(vh);

		} else {
			vh = (ThemeItemViewHolder) convertView.getTag();
		}

		vh.themeName.setText(theme);

		vh.themeId.setOnClickListener(this);
		vh.themeId.setTag(position);
		// Return the completed view to render on screen
		return convertView;
	}

	private static class ThemeItemViewHolder {
		TextView themeName;
		LinearLayout themeId;
	}
}
