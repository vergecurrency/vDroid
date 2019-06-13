package vergecurrency.vergewallet.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;

import vergecurrency.vergewallet.helpers.utils.LanguagesUtils;
import vergecurrency.vergewallet.service.model.Language;

public class LanguagesViewModel extends AndroidViewModel {
	private ArrayList<Language> languages;

	public LanguagesViewModel(@NonNull Application application) {
		super(application);

		languages = LanguagesUtils.loadLanguagesFromFile(getApplication().getApplicationContext());
	}

	public ArrayList<Language> getLanguages() {
		return languages;
	}

}