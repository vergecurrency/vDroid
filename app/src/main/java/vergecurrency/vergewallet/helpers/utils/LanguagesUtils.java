package vergecurrency.vergewallet.helpers.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

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
import vergecurrency.vergewallet.service.model.PreferencesManager;

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

	public static Context onAttach(Context context) {
		String lang = getPersistedData();
		return setLocale(context, lang);
	}

	public static String getLanguage() {
		return getPersistedData();
	}

	public static Context setLocale(Context context, String lang) {
		String language = Language.getLanguageFromJson(lang).getLanguage();
		persist(lang);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			return updateResources(context, language);
		}

		return updateResourcesLegacy(context, language);
	}

	private static String getPersistedData() {
		PreferencesManager pm = PreferencesManager.getInstance();
		return pm.getCurrentLanguage();
	}

	private static void persist(String language) {
		PreferencesManager pm = PreferencesManager.getInstance();
		pm.setCurrentLanguage(language);
	}

	@TargetApi(Build.VERSION_CODES.N)
	private static Context updateResources(Context context, String language) {
		Locale locale = new Locale(language);
		Locale.setDefault(locale);

		Configuration configuration = context.getResources().getConfiguration();
		configuration.setLocale(locale);

		return context.createConfigurationContext(configuration);
	}

	@SuppressWarnings("deprecation")
	private static Context updateResourcesLegacy(Context context, String language) {
		Locale locale = new Locale(language);
		Locale.setDefault(locale);

		Resources resources = context.getResources();

		Configuration configuration = resources.getConfiguration();
		configuration.locale = locale;

		resources.updateConfiguration(configuration, resources.getDisplayMetrics());

		return context;
	}


}
