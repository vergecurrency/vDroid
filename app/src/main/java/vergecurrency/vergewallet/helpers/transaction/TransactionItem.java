package vergecurrency.vergewallet.helpers.transaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public interface TransactionItem {
        public int getViewType();
        public View getView(LayoutInflater inflater, View convertView, ViewGroup parent);
}
