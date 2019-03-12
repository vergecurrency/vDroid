package vergecurrency.vergewallet.viewmodel;

import android.app.Application;

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

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import vergecurrency.vergewallet.Constants;
import vergecurrency.vergewallet.service.model.Transaction;

public class TransactionsViewModel extends AndroidViewModel {

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

			return new ArrayList<>(Arrays.asList(txsArray));
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
}
