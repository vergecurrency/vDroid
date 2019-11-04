package vergecurrency.vergewallet.helpers.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ValidationUtilsTest {

	@Test
	void isStringEightCharsLong() {
		assertTrue(ValidationUtils.INSTANCE.isStringEightCharsLong("12345678"));
		assertTrue(ValidationUtils.INSTANCE.isStringEightCharsLong("verylongstringbaiwanttotseesfsby,.-,%4352"));
		assertFalse(ValidationUtils.INSTANCE.isStringEightCharsLong("1234567"));
		assertFalse(ValidationUtils.INSTANCE.isStringEightCharsLong(""));
		assertFalse(ValidationUtils.INSTANCE.isStringEightCharsLong(null));
	}

	@Test
	void hasStringUppercaseChar() {
		assertTrue(ValidationUtils.INSTANCE.hasStringUppercaseChar("exAmple"));
		assertTrue(ValidationUtils.INSTANCE.hasStringUppercaseChar("EXAMPLE"));
		assertFalse(ValidationUtils.INSTANCE.hasStringUppercaseChar("example"));
		assertFalse(ValidationUtils.INSTANCE.hasStringUppercaseChar("1234.-,"));
		assertFalse(ValidationUtils.INSTANCE.hasStringUppercaseChar(""));
	}

	@Test
	void hasStringLowercaseChar() {
		assertTrue(ValidationUtils.INSTANCE.hasStringLowercaseChar("example"));
		assertTrue(ValidationUtils.INSTANCE.hasStringLowercaseChar("exAmple"));
		assertFalse(ValidationUtils.INSTANCE.hasStringLowercaseChar("EXAMPLE"));
		assertFalse(ValidationUtils.INSTANCE.hasStringLowercaseChar(""));
		assertFalse(ValidationUtils.INSTANCE.hasStringLowercaseChar("1234.-,"));
	}

	@Test
	void isStringContainingSpecialChars() {
		assertTrue(ValidationUtils.INSTANCE.isStringContainingSpecialChars("Hello%123"));
		assertFalse(ValidationUtils.INSTANCE.isStringContainingSpecialChars("Hello123"));
		assertFalse(ValidationUtils.INSTANCE.isStringContainingSpecialChars("123"));
		assertFalse(ValidationUtils.INSTANCE.isStringContainingSpecialChars(""));
		assertFalse(ValidationUtils.INSTANCE.isStringContainingSpecialChars(" "));
	}
}