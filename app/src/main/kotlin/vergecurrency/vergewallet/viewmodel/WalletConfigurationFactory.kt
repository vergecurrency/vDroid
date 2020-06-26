package vergecurrency.vergewallet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import vergecurrency.vergewallet.model.WalletConfiguration

class WalletConfigurationFactory() : ViewModelProvider.Factory {

    companion object {
        var walletConfig: WalletConfiguration? = null

        fun clearWalletConfig() {
            walletConfig = null;
        }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WalletConfiguration::class.java)) {
            if (walletConfig == null) {
                walletConfig = WalletConfiguration()
            }
            return walletConfig as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}