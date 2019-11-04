package vergecurrency.vergewallet.helpers.utils

import java.nio.ByteBuffer
import java.nio.charset.CharacterCodingException
import java.nio.charset.Charset
import java.nio.charset.CharsetDecoder

object ValidationUtils {

    fun isStringEightCharsLong(s: String?): Boolean {
        return if (s != null)
            s.length >= 8
        else
            false
    }

    fun hasStringUppercaseChar(s: String?): Boolean {
        return if (s != null)
            s != s.toLowerCase()
        else
            false
    }

    fun hasStringLowercaseChar(s: String?): Boolean {
        return if (s != null)
            s != s.toUpperCase()
        else
            false
    }

    fun isStringContainingSpecialChars(s: String?): Boolean {
        return if (s != null)
            !s.matches("[A-Za-z0-9 ]*".toRegex())
        else
            false
    }

    fun isValidUTF8(input: String): Boolean {

        val inputArray = input.toByteArray()

        val cs = Charset.forName("UTF-8").newDecoder()

        try {
            cs.decode(ByteBuffer.wrap(inputArray))
            return true
        } catch (e: CharacterCodingException) {
            return false
        }

    }
}
