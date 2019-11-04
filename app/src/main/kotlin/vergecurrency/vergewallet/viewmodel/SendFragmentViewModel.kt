package vergecurrency.vergewallet.viewmodel

import androidx.lifecycle.ViewModel
import vergecurrency.vergewallet.wallet.WalletManager

class SendFragmentViewModel : ViewModel() {

    var balance: Long? = null

    init {
        balance = WalletManager.getBalance().value
    }
}
