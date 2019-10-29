package vergecurrency.vergewallet.helpers.utils;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
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

	public long readUnsignedInt(InputStream in) throws IOException {
		int ch1 = in.read();
		int ch2 = in.read();
		int ch3 = in.read();
		int ch4 = in.read();
		if ((ch1 | ch2 | ch3 | ch4) < 0) {
			throw new EOFException();
		}
		long ln4 = ch4 & 0x00000000ffffffffL;
		return (ln4 << 24) + (ch3 << 16) + (ch2 << 8) + (ch1 << 0);
	}
}
