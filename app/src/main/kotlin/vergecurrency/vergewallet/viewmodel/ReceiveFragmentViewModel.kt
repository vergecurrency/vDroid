package vergecurrency.vergewallet.viewmodel

import androidx.lifecycle.ViewModel
import vergecurrency.vergewallet.wallet.WalletManager

class ReceiveFragmentViewModel : ViewModel() {

    var receiveAddress: String? = null

    init {
        receiveAddress = WalletManager.receiveAddress
    }

    fun newReceiveAddress() {}
}
