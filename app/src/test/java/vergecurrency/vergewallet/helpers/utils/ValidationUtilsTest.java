package vergecurrency.vergewallet.helpers.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ValidationUtilsTest {

	@Test
	void isStringEightCharsLong() {
		assertTrue(ValidationUtils.isStringEightCharsLong("12345678"));
		assertTrue(ValidationUtils.isStringEightCharsLong("verylongstringbaiwanttotseesfsby,.-,%4352"));
		assertFalse(ValidationUtils.isStringEightCharsLong("1234567"));
		assertFalse(ValidationUtils.isStringEightCharsLong(""));
		assertFalse(ValidationUtils.isStringEightCharsLong(null));
	}

	@Test
	void hasStringUppercaseChar() {
		assertTrue(ValidationUtils.hasStringUppercaseChar("exAmple"));
		assertTrue(ValidationUtils.hasStringUppercaseChar("EXAMPLE"));
		assertFalse(ValidationUtils.hasStringUppercaseChar("example"));
		assertFalse(ValidationUtils.hasStringUppercaseChar("1234.-,"));
		assertFalse(ValidationUtils.hasStringUppercaseChar(""));
	}

	@Test
	void hasStringLowercaseChar() {
		assertTrue(ValidationUtils.hasStringLowercaseChar("example"));
		assertTrue(ValidationUtils.hasStringLowercaseChar("exAmple"));
		assertFalse(ValidationUtils.hasStringLowercaseChar("EXAMPLE"));
		assertFalse(ValidationUtils.hasStringLowercaseChar(""));
		assertFalse(ValidationUtils.hasStringLowercaseChar("1234.-,"));
	}

	@Test
	void isStringContainingSpecialChars() {
		assertTrue(ValidationUtils.isStringContainingSpecialChars("Hello%123"));
		assertFalse(ValidationUtils.isStringContainingSpecialChars("Hello123"));
		assertFalse(ValidationUtils.isStringContainingSpecialChars("123"));
		assertFalse(ValidationUtils.isStringContainingSpecialChars(""));
		assertFalse(ValidationUtils.isStringContainingSpecialChars(" "));
	}
}