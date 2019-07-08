package vergecurrency.vergewallet.view.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.service.model.Transaction;
import vergecurrency.vergewallet.view.base.BaseActivity;

import static vergecurrency.vergewallet.view.ui.components.transaction.TransactionListItem.EXTRA_TRANSACTION;

public class TransactionDetailActivity extends BaseActivity implements View.OnClickListener {
    private static Transaction tx;
    private ImageView closeButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);
        tx = (Transaction) getIntent().getSerializableExtra(EXTRA_TRANSACTION);
        TextView transactionShort = findViewById(R.id.transaction_detail_address_short);
        TextView transactionAmount = findViewById(R.id.transaction_detail_amount);
        transactionShort.setText(String.format("%s******", tx.getAddress().substring(0, 6)));
        if (tx.isReceive()) {
            transactionAmount.setText(String.join(" ", "+", new Double(tx.getAmount()).toString()));
            transactionAmount.setTextColor(ContextCompat.getColor(this, R.color.material_green_500));
        } else {
            transactionAmount.setText(String.join(" ", "-", new Double(tx.getAmount()).toString()));
            transactionAmount.setTextColor(ContextCompat.getColor(this, R.color.material_red_500));
        }
        closeButton = findViewById(R.id.transaction_detail_close_button);
        closeButton.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        finish();
    }
}
