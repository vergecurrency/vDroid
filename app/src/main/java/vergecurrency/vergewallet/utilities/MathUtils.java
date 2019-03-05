package vergecurrency.vergewallet.utilities;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class MathUtils {


	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public  static int getRandomNumber(int max) {
		return (int) (Math.random() * max);
	}
}
