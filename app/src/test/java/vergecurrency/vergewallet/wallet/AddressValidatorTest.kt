package vergecurrency.vergewallet.wallet

import org.junit.Test
import org.junit.jupiter.api.Assertions.*

internal class AddressValidatorTest {


    @Test
    fun testValidatingInvalidAddress() {
        val validator : AddressValidator = AddressValidator()

        validator.validate("saga") {valid, address, amount ->
            assertTrue(!valid)
            assertTrue(address == null)
            assertTrue(amount == null)

        }

        validator.validate("DCys4K9buSLAgd4jG9qqZn3vB9CdXJLMJusds") {valid, address, amount ->
            assertTrue(!valid)
            assertTrue(address == null)
            assertTrue(amount == null)
        }
    }

    @Test
    fun testValidationValidAddress() {

        val validator = AddressValidator()

        validator.validate("DCys4K9buSLAgd4jG9qqZn3vB9CdXJLMJu") {valid, address, amount ->
            assertTrue(valid)
            assertTrue(address == "DCys4K9buSLAgd4jG9qqZn3vB9CdXJLMJu")
            assertTrue(amount == null)
        }
    }
}