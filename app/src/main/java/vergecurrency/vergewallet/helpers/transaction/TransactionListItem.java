package vergecurrency.vergewallet.helpers.transaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import java.sql.Date;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.helpers.utils.MathUtils;
import vergecurrency.vergewallet.service.model.Transaction;

public class TransactionListItem implements TransactionItem {
    private final Transaction tx;

    public TransactionListItem(Transaction transaction) {
        this.tx = transaction;
    }

    @Override
    public int getViewType() {
        return TransactionRowType.LIST_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView, ViewGroup parent) {
        TransactionItemViewHolder vh;
        if (convertView == null) {
            vh = new TransactionItemViewHolder();
            convertView = inflater.inflate(R.layout.listview_item_transaction, parent, false);

            vh.txAddress = convertView.findViewById(R.id.listview_transaction_item_address);
            vh.txAmount = convertView.findViewById(R.id.listview_transaction_item_amount);
            vh.txDateTime = convertView.findViewById(R.id.listview_transaction_item_datetime);
            vh.txIcon = convertView.findViewById(R.id.listview_transaction_item_icon);

            convertView.setTag(vh);

        } else {
            vh = (TransactionItemViewHolder) convertView.getTag();
        }

        if (tx.getCategory().equals("send")) {
            vh.txAmount.setText(String.format("- %s XVG", MathUtils.round(tx.getAmount(), 2)));
            vh.txAmount.setTextColor(ContextCompat.getColor(convertView.getContext(), R.color.material_red_500));
            vh.txIcon.setImageResource(R.drawable.icon_arrow_up);
            DrawableCompat.setTint(vh.txIcon.getDrawable(), ContextCompat.getColor(convertView.getContext(), R.color.material_red_500));
        } else if (tx.getCategory().equals("receive")) {
            vh.txAmount.setText(String.format("+ %s XVG", MathUtils.round(tx.getAmount(), 2)));
            vh.txAmount.setTextColor(ContextCompat.getColor(convertView.getContext(), R.color.material_green_500));
            vh.txIcon.setImageResource(R.drawable.icon_arrow_down);
            DrawableCompat.setTint(vh.txIcon.getDrawable(), ContextCompat.getColor(convertView.getContext(), R.color.material_green_500));
        }


        vh.txAddress.setText(String.format("%s******", tx.getAddress().substring(0, 6)));
        vh.txDateTime.setText(new Date(tx.getTime() * 1000).toString());

        //vh.txAddress.setOnClickListener(this);
        //vh.txAddress.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }

    /**
     * Internal struct containing tx info
     */
    private static class TransactionItemViewHolder {
        ImageView txIcon;
        TextView txAddress;
        TextView txDateTime;
        TextView txAmount;
    }

}
