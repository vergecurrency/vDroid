package vergecurrency.vergewallet.helpers.transaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.common.StringUtils;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.helpers.utils.MathUtils;
import vergecurrency.vergewallet.service.model.Transaction;

public class TransactionListItem implements TransactionItem, View.OnClickListener {
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd LLLL yyyy");
    private static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("HH:mm");
    private final Transaction tx;

    public TransactionListItem(Transaction transaction) {
        this.tx = transaction;
    }

    @Override
    public int getViewType() {
        return TransactionRowType.LIST_ITEM.ordinal();
    }


    /**
     * OnClick listener to show up transaction details
     * TODO: display a view with the details, for now just the address is shown up on a snackbar
     * That said, isn't "Snackbar" class name funny? Why not "KinderBueno"? It's a snack, no?
     *
     * @param v the clicked view.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.listview_transaction_id) {
            Snackbar.make(v, "Release date " + tx.getAddress(), Snackbar.LENGTH_LONG)
                    .setAction("No action", null).show();
        }
    }


    @Override
    public View getView(LayoutInflater inflater, View convertView, ViewGroup parent, int position) {
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

        if (tx.isSend()) {
            vh.txAmount.setText(String.format("- %s XVG", MathUtils.round(tx.getAmount(), 2)));
            vh.txAmount.setTextColor(ContextCompat.getColor(convertView.getContext(), R.color.material_red_500));
            vh.txIcon.setImageResource(R.drawable.icon_arrow_up);
            DrawableCompat.setTint(vh.txIcon.getDrawable(), ContextCompat.getColor(convertView.getContext(), R.color.material_red_500));
        } else if (tx.isReceive()) {
            vh.txAmount.setText(String.format("+ %s XVG", MathUtils.round(tx.getAmount(), 2)));
            vh.txAmount.setTextColor(ContextCompat.getColor(convertView.getContext(), R.color.material_green_500));
            vh.txIcon.setImageResource(R.drawable.icon_arrow_down);
            DrawableCompat.setTint(vh.txIcon.getDrawable(), ContextCompat.getColor(convertView.getContext(), R.color.material_green_500));
        }

        if (tx.getAccount() == null) {
            vh.txAddress.setText(String.format("%s******", tx.getAddress().substring(0, 6)));
        } else {
            vh.txAddress.setText(tx.getAccount());
        }
        Date date = new Date(tx.getTime() * 1000);
        vh.txDateTime.setText(String.join(" ", DATE_FORMATTER.format(date), "at", TIME_FORMATTER.format(date)));

        vh.txAddress.setOnClickListener(this);
        vh.txAddress.setTag(position);
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
