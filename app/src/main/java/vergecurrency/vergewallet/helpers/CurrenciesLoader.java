package vergecurrency.vergewallet.helpers;

import android.content.Context;

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

import vergecurrency.vergewallet.structs.Currency;

public final class CurrenciesLoader {

	public CurrenciesLoader() {

	}


	public static ArrayList<Currency> loadCurrenciesFromFile(Context c, String fileName) {
		JSONParser parser = new JSONParser();
		ArrayList<Currency> currencies;
		try {
			InputStream is = c.getAssets().open(fileName);
			InputStreamReader isr = new InputStreamReader(is);
			JSONObject jsonObject = (JSONObject) parser.parse(isr);
			JSONArray currenciesJSON = (JSONArray) jsonObject.get("currencies");


			Currency[] txsArray;
			txsArray = new GsonBuilder().create().fromJson(currenciesJSON.toJSONString(), Currency[].class);
			currencies = new ArrayList<>(Arrays.asList(txsArray));


		} catch (IOException e) {
			currencies = null;
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
			currencies = null;
		}

		return currencies;
	}

}
