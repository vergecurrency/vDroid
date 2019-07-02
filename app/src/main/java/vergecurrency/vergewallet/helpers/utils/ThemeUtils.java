package vergecurrency.vergewallet.helpers.utils;

import android.app.Activity;
import android.content.Context;

import vergecurrency.vergewallet.R;
public final class ThemeUtils {

	public static void setTheme(String theme, Context context, boolean recreateActivity) {
		if (theme.equals("Feather Theme")) {
			context.setTheme(R.style.FeatherTheme);
		} else if (theme.equals("Moon Mode")) {
			context.setTheme(R.style.MoonMode);
		}

		if(recreateActivity) {
			((Activity) context).recreate();
		}
	}
}
