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
    }
}