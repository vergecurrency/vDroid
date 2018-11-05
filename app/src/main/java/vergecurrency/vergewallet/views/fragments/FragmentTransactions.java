package vergecurrency.vergewallet.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.gson.GsonBuilder;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.structs.Transaction;
import vergecurrency.vergewallet.views.fragments.workers.TransactionsAdapter;

public class FragmentTransactions extends Fragment {
    
    double balance = 10d;
    ListView transactionList;
    
    public FragmentTransactions() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	
		View rootView;
        // Inflate the layout for this fragment
		if (balance <= 0)
        rootView = inflater.inflate(R.layout.fragment_notransactions, container, false);
		else {
			rootView = inflater.inflate(R.layout.fragment_transactions, container, false);
			transactionList = rootView.findViewById(R.id.transactions_listview);
			fillTransactionList(transactionList);
		}
		
		return rootView;
    }
    
    private void fillTransactionList (ListView transactionList) {
		JSONParser parser = new JSONParser();
		try {
			//Get the Json
			InputStream is = this.getContext().getAssets().open("transactions.json");
			InputStreamReader isr = new InputStreamReader(is);
			JSONObject jsonObject= (JSONObject) parser.parse(isr);
			JSONArray transactionsListJSON = (JSONArray) jsonObject.get("transactions");
			
			Transaction[] txs;
			txs = new GsonBuilder().create().fromJson(transactionsListJSON.toJSONString(), Transaction[].class) ;
			
			//ArrayAdapter<Transaction> adapter = new ArrayAdapter<Transaction>(this.getContext(), android.R.layout.simple_list_item_1, txs);
			
			transactionList.setAdapter(new TransactionsAdapter(this.getContext(), txs));
			
			
		} catch (Exception ex) {
			ex.getStackTrace();
		}
	}
	
	
}
