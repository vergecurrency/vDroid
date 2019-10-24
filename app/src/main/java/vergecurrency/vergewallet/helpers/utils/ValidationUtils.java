package vergecurrency.vergewallet.helpers.utils;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

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

	public static boolean isValidUTF8( String input) {

		byte[] inputArray = input.getBytes();

		CharsetDecoder cs = Charset.forName("UTF-8").newDecoder();

		try {
			cs.decode(ByteBuffer.wrap(inputArray));
			return true;
		}
		catch(CharacterCodingException e){
			return false;
		}
	}
}
