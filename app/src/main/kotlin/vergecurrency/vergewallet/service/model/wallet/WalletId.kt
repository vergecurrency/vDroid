package vergecurrency.vergewallet.service.model.wallet

import com.google.gson.Gson
import io.horizontalsystems.bitcoinkit.core.Wallet

class WalletId {

    var identifier: String? = null

    companion object {

        fun decode(JSON: String): WalletId {
            return Gson().fromJson(JSON, WalletId::class.java)
        }
    }
}
