package vergecurrency.vergewallet.helpers.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.akexorcist.localizationactivity.core.LocalizationActivityDelegate;
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
import java.util.Locale;

import vergecurrency.vergewallet.Constants;
import vergecurrency.vergewallet.service.model.Language;

public final class LanguagesUtils {

	public LanguagesUtils() {

	}


	public static ArrayList<Language> loadLanguagesFromFile(Context c) {
		JSONParser parser = new JSONParser();
		ArrayList<Language> languages;
		try {
			InputStream is = c.getAssets().open(Constants.LANGUAGES_FILE_PATH);
			InputStreamReader isr = new InputStreamReader(is);
			JSONObject jsonObject = (JSONObject) parser.parse(isr);
			JSONArray languagesJSON = (JSONArray) jsonObject.get("languages");


			Language[] languagesArray;
			languagesArray = new GsonBuilder().create().fromJson(languagesJSON.toJSONString(), Language[].class);
			languages = new ArrayList<>(Arrays.asList(languagesArray));


		} catch (IOException e) {
			languages = null;
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
			languages = null;
		}

		return languages;
	}


	public static void setLanguage(String language, Context c) {
		Resources res = c.getResources();
		Configuration conf = new Configuration(res.getConfiguration());
		conf.setLocale(new Locale(language));
		c.createConfigurationContext(conf);
	}


}
