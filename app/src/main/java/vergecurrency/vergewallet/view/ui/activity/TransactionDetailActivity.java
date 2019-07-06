package vergecurrency.vergewallet.view.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;

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
        //vh.txAddress.setText(String.format("%s******", tx.getAddress().substring(0, 6)));
        closeButton = findViewById(R.id.transaction_detail_close_button);
        closeButton.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        finish();
    }
}
