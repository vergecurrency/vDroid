package vergecurrency.vergewallet.service.model.wallet

class UnspentOutput {

    var address: String? = null
    var confirmations: Int = 0
    var satoshis: Long = 0
    var scriptPubKey: String? = null
    var txID: String? = null
    var vout: Int = 0
    var publicKeys: Array<String>? = null
    var path: String? = null
    var locked: Boolean = false


    //todo : fuck it I'll do it later. Another funny swift vs ios struggle
    //enum class CodingKeys : String, CodingKey {
    enum class CodingKeys {
        address,
        confirmations,
        satoshis,
        scriptPubKey,
        txID,
        vout,
        publicKeys,
        path,
        locked
    }


}
