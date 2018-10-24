package vergecurrency.vergewallet.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

import vergecurrency.vergewallet.R;

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
			JSONObject jsonObject= (JSONObject) parser.parse(new FileReader("vergecurrency/vergewallet/previewdata/transactions.json"));
			
			JSONArray transactionsListJSON = (JSONArray) jsonObject.get("transactions");
			
			//TODO : what I didn't do today because I was too busy listening to Billy Joel
			
		} catch (Exception ex) {
		
		}
	}
}
