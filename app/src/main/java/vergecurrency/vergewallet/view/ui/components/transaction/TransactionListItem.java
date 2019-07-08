package vergecurrency.vergewallet.view.ui.components.transaction;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.helpers.utils.MathUtils;
import vergecurrency.vergewallet.service.model.Transaction;
import vergecurrency.vergewallet.view.ui.activity.TransactionDetailActivity;
import vergecurrency.vergewallet.view.ui.activity.error.ErrorRecoveryActivity;

import static vergecurrency.vergewallet.helpers.utils.TransactionUtils.DATE_FORMATTER;
import static vergecurrency.vergewallet.helpers.utils.TransactionUtils.EXTRA_TRANSACTION;
import static vergecurrency.vergewallet.helpers.utils.TransactionUtils.TIME_FORMATTER;

public class TransactionListItem implements TransactionItem, View.OnClickListener {
    private final Transaction tx;

    public TransactionListItem(Transaction transaction) {
        this.tx = transaction;
    }

    @Override
    public int getViewType() {
        return TransactionRowType.LIST_ITEM.ordinal();
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), TransactionDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra(EXTRA_TRANSACTION, tx);
        v.getContext().startActivity(intent);
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
            if (tx.isReceive()) {
                vh.txAddress.setText("Received");
            } else {
                vh.txAddress.setText("Sent");
            }
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
