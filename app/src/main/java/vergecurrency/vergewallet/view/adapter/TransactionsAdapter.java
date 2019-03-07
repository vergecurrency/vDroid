package vergecurrency.vergewallet.view.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Date;
import java.util.ArrayList;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.utilities.MathUtils;
import vergecurrency.vergewallet.service.model.Transaction;

public class TransactionsAdapter extends ArrayAdapter<Transaction> implements View.OnClickListener {
	Context context;
	ArrayList<Transaction> transactions;

	/**
	 * Internal struct containing transaction info
	 */
	private static class TransactionItemViewHolder {
		ImageView txIcon;
		TextView txAddress;
		TextView txDateTime;
		TextView txAmount;
	}

	/**
	 * Transaction adapter constructor
	 *
	 * @param context the application context
	 * @param txs     the transactions list we need to display
	 */
	public TransactionsAdapter(@NonNull Context context, ArrayList<Transaction> txs) {
		super(context, R.layout.listview_item_transaction, txs);
		this.context = context;
		this.transactions = txs;
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

		int position = (Integer) v.getTag();
		Object object = getItem(position);
		Transaction tx = (Transaction) object;

		switch (v.getId()) {
			case R.id.listview_transaction_id:
				Snackbar.make(v, "Release date " + tx.getAddress(), Snackbar.LENGTH_LONG)
						.setAction("No action", null).show();
				break;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Transaction tx = getItem(position);
		TransactionItemViewHolder vh;

		if (convertView == null) {
			vh = new TransactionItemViewHolder();
			LayoutInflater inflater = LayoutInflater.from(getContext());
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
			vh.txAmount.setText("- " + (Double.toString(MathUtils.round(tx.getAmount(), 2))) + " XVG");
			vh.txAmount.setTextColor(getContext().getResources().getColor(R.color.material_red_500));
			vh.txIcon.setImageResource(R.drawable.icon_arrow_up);
			DrawableCompat.setTint(vh.txIcon.getDrawable(), ContextCompat.getColor(context, R.color.material_red_500));
		} else if (tx.getCategory().equals("receive")) {
			vh.txAmount.setText("+ " + (Double.toString(MathUtils.round(tx.getAmount(), 2))) + " XVG");
			vh.txAmount.setTextColor(getContext().getResources().getColor(R.color.material_green_500));
			vh.txIcon.setImageResource(R.drawable.icon_arrow_down);
			DrawableCompat.setTint(vh.txIcon.getDrawable(), ContextCompat.getColor(context, R.color.material_green_500));
		}


		vh.txAddress.setText(String.format("%s******", tx.getAddress().substring(0, 6)));
		vh.txDateTime.setText(new Date(tx.getTime() * 1000).toString());

		vh.txAddress.setOnClickListener(this);
		vh.txAddress.setTag(position);
		// Return the completed view to render on screen
		return convertView;
	}


}