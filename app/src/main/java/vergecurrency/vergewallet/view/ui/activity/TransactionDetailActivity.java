package vergecurrency.vergewallet.view.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.Date;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.service.model.Transaction;
import vergecurrency.vergewallet.view.base.BaseActivity;

import static vergecurrency.vergewallet.helpers.utils.TransactionUtils.DATE_FORMATTER;
import static vergecurrency.vergewallet.helpers.utils.TransactionUtils.EXTRA_TRANSACTION;
import static vergecurrency.vergewallet.helpers.utils.TransactionUtils.TIME_FORMATTER;

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
        TextView transactionTime = findViewById(R.id.transaction_detail_time);

        transactionShort.setText(String.format("%s******", tx.getAddress().substring(0, 6)));
        Date date = new Date(tx.getTime() * 1000);
        transactionTime.setText(String.join(" ", DATE_FORMATTER.format(date), "at", TIME_FORMATTER.format(date)));

        if (tx.isReceive()) {
            transactionAmount.setText(String.join(" ", "+", new Double(tx.getAmount()).toString(), "XVG"));
            transactionAmount.setTextColor(ContextCompat.getColor(this, R.color.material_green_500));
        } else {
            transactionAmount.setText(String.join(" ", "-", new Double(tx.getAmount()).toString(), "XVG"));
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
