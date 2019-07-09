package vergecurrency.vergewallet.view.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.service.model.Transaction;
import vergecurrency.vergewallet.view.base.BaseActivity;

import static vergecurrency.vergewallet.helpers.utils.TransactionUtils.EXTRA_TRANSACTION;
import static vergecurrency.vergewallet.helpers.utils.TransactionUtils.toFormattedDate;

public class TransactionDetailActivity extends BaseActivity implements View.OnClickListener {
    private static Transaction tx;
    private ImageView closeButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);

        //Get Transaction
        tx = (Transaction) getIntent().getSerializableExtra(EXTRA_TRANSACTION);

        //View Elements
        TextView transactionShort = findViewById(R.id.transaction_detail_address_short);
        TextView transactionAmount = findViewById(R.id.transaction_detail_amount);
        TextView transactionTime = findViewById(R.id.transaction_detail_time);
        TextView transactionAddress = findViewById(R.id.transaction_detail_address_item_address);
        TextView transactionMessage = findViewById(R.id.transaction_detail_message_item_message);
        TextView transactionId = findViewById(R.id.transaction_detail_txid_item_txid);
        TextView transactionConfirmations = findViewById(R.id.transaction_detail_confirmations_item_confirmations);
        View messageLayout = findViewById(R.id.transaction_detail_message_item);


        transactionShort.setText(String.format("%s******", tx.getAddress().substring(0, 6)));
        transactionTime.setText(toFormattedDate(tx.getTime()));

        //Green or red amount +/-
        if (tx.isReceive()) {
            transactionAmount.setText(String.join(" ", "+", new Double(tx.getAmount()).toString(), "XVG"));
            transactionAmount.setTextColor(ContextCompat.getColor(this, R.color.material_green_500));
        } else {
            transactionAmount.setText(String.join(" ", "-", new Double(tx.getAmount()).toString(), "XVG"));
            transactionAmount.setTextColor(ContextCompat.getColor(this, R.color.material_red_500));
        }

        transactionAddress.setText(tx.getAddress());
        transactionConfirmations.setText(Integer.toString(tx.getConfirmations()));
        transactionId.setText(tx.getTxid());
        transactionMessage.setText(tx.getAccount());

        if (tx.getAccount() == null || tx.getAccount().equals("")) {
            messageLayout.setVisibility(View.INVISIBLE);
        }

        closeButton = findViewById(R.id.transaction_detail_close_button);
        closeButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        finish();
    }
}
