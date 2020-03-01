package vergecurrency.vergewallet.wallet


import io.horizontalsystems.bitcoinkit.exceptions.AddressFormatException
import io.horizontalsystems.bitcoinkit.network.MainNet
import io.horizontalsystems.bitcoinkit.utils.AddressConverter
import java.util.*

typealias ValidationCompletion = (valid: Boolean, address: String?, amount: Float?) -> Unit

class AddressValidator {

    //implement fun override for barcode object

    fun validate(string: String, completion: ValidationCompletion) {
        var valid = false
        var address: String? = null
        var amount: Float? = null


        if (isValidAddress(string)) {
            valid = true
            address = string
        }


        val splitRequest = string.replace("verge://", "").replace("verge:", "").split("\\?".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        val parametersString = splitRequest.last().split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        if (isValidAddress(splitRequest.first())) {
            valid = true
            address = splitRequest.first()
        } else {
            return completion(valid, address, amount)
        }

        val splitParameters = parametersString.last().split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        val parameters = HashMap<String, String>()

        for (param in splitParameters) {
            val parameterPair = param.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            parameters[parameterPair.first()] = parameterPair.last()
        }
        val amountParam = parameters.get("amount")

        if (amountParam != null) {
            amount = java.lang.Float.parseFloat(amountParam);
        }

        return completion(valid, address, amount)

    }


    //inner struct
    //inner class ValidationCompletion(var isValid: Boolean, var address: String, var amount: Float?)


    companion object {

        fun isValidAddress(address: String): Boolean {
            try {
                AddressConverter(MainNet()).convert(address)
                return true
            } catch (e: AddressFormatException) {
                System.err.println(e.message)
                return false
            }


        }
    }
}
