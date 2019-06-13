package vergecurrency.vergewallet.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.jakewharton.processphoenix.ProcessPhoenix;

import java.util.ArrayList;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.helpers.utils.LanguagesUtils;
import vergecurrency.vergewallet.service.model.Currency;
import vergecurrency.vergewallet.service.model.Language;
import vergecurrency.vergewallet.service.model.PreferencesManager;

public class LanguagesAdapter extends ArrayAdapter<Language> implements View.OnClickListener {

	private PreferencesManager pm;
	private Activity a;

	/**
	 * Transaction adapter constructor
	 *
	 * @param context the application context
	 * @param langs    the languages list we need to display
	 */
	public LanguagesAdapter(@NonNull Context context, ArrayList<Language> langs) {
		super(context, R.layout.listview_item_language, langs);
		pm = PreferencesManager.getInstance();
		this.a = a;
	}

	@Override
	public void onClick(View v) {
		int position = (Integer) v.getTag();
		Object object = getItem(position);
		Language lang = (Language) object;

		switch (v.getId()) {
			case R.id.listview_language_item:
				Toast.makeText(v.getContext(), "Language chosen : " + lang.getName(), Toast.LENGTH_SHORT).show();
				pm.setCurrentLanguage(lang.getLanguageAsJson());
				LanguagesUtils.setLanguage(lang.getLanguage(), getContext());
				ProcessPhoenix.triggerRebirth(getContext());
				break;
		}

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Language lang = getItem(position);
		LanguageItemViewHolder vh;

		if (convertView == null) {
			vh = new LanguageItemViewHolder();
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.listview_item_language, parent, false);
			vh.languageName = convertView.findViewById(R.id.listview_language_name);

			convertView.setTag(vh);

		} else {
			vh = (LanguageItemViewHolder) convertView.getTag();
		}

		vh.languageName.setText(lang.getName());

		vh.languageId.setOnClickListener(this);
		vh.languageId.setTag(position);
		// Return the completed view to render on screen
		return convertView;
	}

	private static class LanguageItemViewHolder {
		TextView languageName;
		LinearLayout languageId;
	}
}
