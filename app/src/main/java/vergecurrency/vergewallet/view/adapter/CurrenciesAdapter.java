package vergecurrency.vergewallet.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.service.model.Currency;
import vergecurrency.vergewallet.service.model.PreferencesManager;
import vergecurrency.vergewallet.view.ui.activity.settings.ChooseCurrencyActivity;

public class CurrenciesAdapter extends ArrayAdapter<Currency> implements View.OnClickListener {

	/**
	 * Transaction adapter constructor
	 *
	 * @param context the application context
	 * @param curs    the currencies list we need to display
	 */
	public CurrenciesAdapter(@NonNull Context context, ArrayList<Currency> curs) {
		super(context, R.layout.listview_item_currency, curs);
	}

	@Override
	public void onClick(View v) {
		int position = (Integer) v.getTag();
		Currency currency = getItem(position);

		if (v.getId() == R.id.listview_currency_item) {
			Toast.makeText(v.getContext(), "Currency chosen : " + currency.getName(), Toast.LENGTH_SHORT).show();
			PreferencesManager.setSelectedCurrency(currency.getCurrencyAsJSON());
		}

		((ChooseCurrencyActivity)getContext()).finish();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Currency cur = getItem(position);
		CurrencyItemViewHolder vh;

		if (convertView == null) {
			vh = new CurrencyItemViewHolder();
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.listview_item_currency, parent, false);
			vh.currencyId = convertView.findViewById(R.id.listview_currency_item);
			vh.currencyCurrency = convertView.findViewById(R.id.listview_currency_currency);
			vh.currencyName = convertView.findViewById(R.id.listview_currency_name);

			convertView.setTag(vh);

		} else {
			vh = (CurrencyItemViewHolder) convertView.getTag();
		}

		vh.currencyCurrency.setText(cur.getCurrency());
		vh.currencyName.setText(cur.getName());

		vh.currencyId.setOnClickListener(this);
		vh.currencyId.setTag(position);
		// Return the completed view to render on screen
		return convertView;
	}

	private static class CurrencyItemViewHolder {
		TextView currencyCurrency;
		TextView currencyName;
		LinearLayout currencyId;
	}
}
