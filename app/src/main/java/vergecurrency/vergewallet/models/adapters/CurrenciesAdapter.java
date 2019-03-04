package vergecurrency.vergewallet.models.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.models.dataproc.PreferencesManager;
import vergecurrency.vergewallet.structs.Currency;
import vergecurrency.vergewallet.views.activities.MainActivity;
import vergecurrency.vergewallet.views.activities.settings.SelectCurrencyActivity;
import vergecurrency.vergewallet.views.fragments.FragmentSettings;

import static android.support.v4.content.ContextCompat.startActivity;

public class CurrenciesAdapter extends ArrayAdapter<Currency> implements View.OnClickListener {


    private PreferencesManager pm;


    /**
     * Transaction adapter constructor
     *
     * @param context the application context
     * @param curs    the currencies list we need to display
     */
    public CurrenciesAdapter(@NonNull Context context, ArrayList<Currency> curs) {
        super(context, R.layout.listview_item_currency, curs);
        pm = new PreferencesManager(context);



    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object object = getItem(position);
        Currency cur = (Currency) object;

        switch (v.getId()) {
            case R.id.listview_currency_item:
                Toast.makeText(v.getContext(), "Currency chosen : " + cur.getName(), Toast.LENGTH_SHORT).show();
                pm.setSelectedCurrency(cur.getCurrencyAsJSON());
                break;
        }

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Currency cur = getItem(position);
        CurrencyItemViewHolder vh;

        if (convertView == null) {
            vh = new CurrencyItemViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listview_item_currency, parent, false);
            vh.currencyId = (LinearLayout) convertView.findViewById(R.id.listview_currency_item);
            vh.currencyCurrency = (TextView) convertView.findViewById(R.id.listview_currency_currency);
            vh.currencyName = (TextView) convertView.findViewById(R.id.listview_currency_name);
            vh.currencySymbol = (TextView) convertView.findViewById(R.id.listview_currency_symbol);
            convertView.setTag(vh);


        } else {
            vh = (CurrencyItemViewHolder) convertView.getTag();
        }

        vh.currencyCurrency.setText(cur.getCurrency());
        vh.currencyName.setText(cur.getName());
        vh.currencySymbol.setText(cur.getSymbol());

        vh.currencyId.setOnClickListener(this);
        vh.currencyId.setTag(position);
        // Return the completed view to render on screen


        return convertView;

    }



    private static class CurrencyItemViewHolder {
        TextView currencyCurrency;
        TextView currencyName;
        TextView currencySymbol;
        LinearLayout currencyId;
    }


}
