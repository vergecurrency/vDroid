package vergecurrency.vergewallet.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.gson.GsonBuilder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import vergecurrency.vergewallet.Constants;
import vergecurrency.vergewallet.service.model.Transaction;
import vergecurrency.vergewallet.service.model.TransactionSortOrder;

public class TransactionsViewModel extends AndroidViewModel {
    private TransactionSortOrder sortOrder = TransactionSortOrder.TIME_RECEIVED_DESC;

	public TransactionsViewModel(@NonNull Application application) {
		super(application);
	}

	public ArrayList<Transaction> getTransactionsList() {

		JSONParser parser = new JSONParser();

		try {
			InputStream is = getApplication().getBaseContext().getAssets().open(Constants.MOCK_TRANSACTIONS_FILE_PATH);

			JSONObject jsonObject = (JSONObject) parser.parse(new InputStreamReader(is));
			JSONArray transactionsListJSON = (JSONArray) jsonObject.get("transactions");

			Transaction[] txsArray = new GsonBuilder().create().fromJson(transactionsListJSON.toJSONString(), Transaction[].class);
            ArrayList<Transaction> transactions = new ArrayList<>(Arrays.asList(txsArray));
            sort(transactions);
			return transactions;
		} catch (IOException e) {
			//TODO : Catch Exception properly
			e.printStackTrace();
			return null;
		} catch (ParseException e) {
			//TODO : Catch exception properly
			e.printStackTrace();
			return null;
		}
	}

	public double getBalance() {
		return 10d;
	}


    private void sort(ArrayList transactions) {
        switch (sortOrder) {
            case AMOUNT_ASC:
                Collections.sort(transactions, Transaction.AmountComparatorASC);
                break;
            case AMOUNT_DESC:
                Collections.sort(transactions, Transaction.AmountComparatorDESC);
                break;
            case ADDRESS_ASC:
                Collections.sort(transactions, Transaction.AddressComparatorASC);
                break;
            case ADDRESS_DESC:
                Collections.sort(transactions, Transaction.AddressComparatorDESC);
                break;
            case TIME_RECEIVED_ASC:
                Collections.sort(transactions, Transaction.TimeReceivedComparatorASC);
                break;
            case TIME_RECEIVED_DESC:
                Collections.sort(transactions, Transaction.TimeReceivedComparatorDESC);
                break;
            default:
                Collections.sort(transactions, Transaction.TimeReceivedComparatorDESC);
                break;
        }
    }

    public void setSortOrder(TransactionSortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }
}
