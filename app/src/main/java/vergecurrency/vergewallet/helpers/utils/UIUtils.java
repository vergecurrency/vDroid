package vergecurrency.vergewallet.helpers.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.TypedValue;

import androidx.annotation.ColorInt;

import vergecurrency.vergewallet.R;
public final class UIUtils {

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

	public static void restartApplication(Context c) {
		Intent i = c.getPackageManager()
				.getLaunchIntentForPackage( c.getPackageName() );
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		c.startActivity(i);
	}

	public static int resolveAttr(int attr, Context c) {
		TypedValue typedValue = new TypedValue();
		Resources.Theme theme = c.getTheme();
		boolean ra = theme.resolveAttribute(attr, typedValue, true);

		int color = typedValue.resourceId;
		return color;
	}
}
