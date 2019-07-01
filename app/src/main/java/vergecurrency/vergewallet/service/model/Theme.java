package vergecurrency.vergewallet.service.model;

import com.google.gson.Gson;

public class Theme {

	private String theme;
	private String iconPath;

	public Theme() {

	}

	public Theme(String iconPath, String theme) {
		this.iconPath = iconPath;
		this.theme = theme;
	}

	public static Theme getThemeFromJSON(String json) {
		return new Gson().fromJson(json, Theme.class);
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public String getThemesAsJSON() {
		return new Gson().toJson(this);
	}
}
