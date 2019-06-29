package vergecurrency.vergewallet.helpers.transaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import vergecurrency.vergewallet.R;

public class TransactionHeaderItem implements TransactionItem {
    private final String name;

    public TransactionHeaderItem(String name) {
        this.name = name;
    }

    @Override
    public int getViewType() {
        return TransactionRowType.HEADER_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView, ViewGroup parent, int position) {
        View view;
        if (convertView == null) {
            view = inflater.inflate(R.layout.listview_item_transactions_header, null);
        } else {
            view = convertView;
        }
        TextView txv = view.findViewById(R.id.listview_transactions_header_title);
        txv.setText(name);
        return view;
    }

}
