package vergecurrency.vergewallet.wallet


import java.util.HashMap

import io.horizontalsystems.bitcoinkit.exceptions.AddressFormatException
import io.horizontalsystems.bitcoinkit.models.LegacyAddress
import io.horizontalsystems.bitcoinkit.network.MainNet
import io.horizontalsystems.bitcoinkit.utils.AddressConverter

class AddressValidator {

    fun validate(address: String): ValidationCompletion {
        val vc = ValidationCompletion(false, "", 0f)
        if (AddressValidator.isValidAddress(address)) {
            vc.isValid = true
            vc.address = address
        }

        val splittedRequest = address.replace("verge://", "").replace("verge:", "").split("\\?".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        val parameters = splittedRequest[splittedRequest.size - 1].split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        if (AddressValidator.isValidAddress(splittedRequest[0])) {
            vc.isValid = true
            vc.address = splittedRequest[0]
        } else {
            return vc
        }

        val splittedParameters = parameters[parameters.size - 1].split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        val definitiveParameters = HashMap<String, String>()

        for (param in splittedParameters) {
            val parameterPair = param.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            definitiveParameters[parameterPair[0]] = parameterPair[1]
        }
        val stringAmount = (definitiveParameters as Map<String, String>).getOrDefault("amount", "0f")
        var amount = 0f
        if(stringAmount != null) {
            amount = java.lang.Float.parseFloat(stringAmount);
        }
        vc.amount = amount

        return vc
    }


    //inner struct
    inner class ValidationCompletion(var isValid: Boolean, var address: String, var amount: Float?)

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
