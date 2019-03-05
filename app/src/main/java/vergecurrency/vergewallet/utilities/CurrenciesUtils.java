package vergecurrency.vergewallet.utilities;

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

import vergecurrency.vergewallet.Constants;
import vergecurrency.vergewallet.service.model.Currency;

public final class CurrenciesUtils {

	public CurrenciesUtils() {

	}


	public static ArrayList<Currency> loadCurrenciesFromFile(Context c) {
		JSONParser parser = new JSONParser();
		ArrayList<Currency> currencies;
		try {
			InputStream is = c.getAssets().open(Constants.CURRENCIES_FILE_PATH);
			InputStreamReader isr = new InputStreamReader(is);
			JSONObject jsonObject = (JSONObject) parser.parse(isr);
			JSONArray currenciesJSON = (JSONArray) jsonObject.get("currencies");


			Currency[] currenciesArray;
			currenciesArray = new GsonBuilder().create().fromJson(currenciesJSON.toJSONString(), Currency[].class);
			currencies = new ArrayList<>(Arrays.asList(currenciesArray));


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
