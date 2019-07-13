package vergecurrency.vergewallet.helpers.utils;

public final class ValidationUtils {

	public static boolean isStringEightCharsLong(String s) {
		if(s!=null)
		return s.length() >= 8;
		else return false;
	}

	public static boolean hasStringUppercaseChar(String s) {
		if(s!=null)
			return !s.equals(s.toLowerCase());
		else return false;
	}

	public static boolean hasStringLowercaseChar(String s) {
		if(s!=null)
			return !s.equals(s.toUpperCase());
		else return false;
	}

	public static boolean isStringContainingSpecialChars(String s) {
		if(s!=null)
			return !s.matches("[A-Za-z0-9 ]*");
		else return false;
	}
}
