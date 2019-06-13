package vergecurrency.vergewallet.service.model;

import com.google.gson.Gson;

public class Language {

	private String language;
	private String name;

	public Language() {

	}

	public Language(String name, String language) {
		this.name = name;
		this.language = language;
	}

	public static Language getLanguageFromJson(String json) {
		return new Gson().fromJson(json, Language.class);
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLanguageAsJson() {
		return new Gson().toJson(this);
	}
}
