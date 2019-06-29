package vergecurrency.vergewallet.helpers.transaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public interface TransactionItem {
        int getViewType();
        View getView(LayoutInflater inflater, View convertView, ViewGroup parent, int position);
}
