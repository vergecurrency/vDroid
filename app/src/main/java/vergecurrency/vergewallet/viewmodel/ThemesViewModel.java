package vergecurrency.vergewallet.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;

import vergecurrency.vergewallet.helpers.utils.LanguagesUtils;
import vergecurrency.vergewallet.service.model.Language;

public class ThemesViewModel extends AndroidViewModel {
	private ArrayList<Language> languages;

	public ThemesViewModel(@NonNull Application application) {
		super(application);

		//languages = LanguagesUtils.loadLanguagesFromFile(getApplication().getApplicationContext());
	}

	/*public ArrayList<Language> getThemes() {
		return languages;
	}*/

	public ArrayList<String> getThemes() { ArrayList<String> a  = new ArrayList<>();
	a.add("Feather Theme");
	a.add("Moon Mode");
	return a;
	}

}