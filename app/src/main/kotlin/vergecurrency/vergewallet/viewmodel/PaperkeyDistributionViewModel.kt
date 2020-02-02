package vergecurrency.vergewallet.viewmodel

import androidx.lifecycle.ViewModel
import vergecurrency.vergewallet.service.model.EncryptedPreferencesManager
import vergecurrency.vergewallet.service.model.MnemonicManager
import vergecurrency.vergewallet.service.model.PreferencesManager
import vergecurrency.vergewallet.wallet.WalletManager

class PaperkeyDistributionViewModel : ViewModel() {

    lateinit var seed: Array<String>

    init {
        if (!PreferencesManager.isFirstLaunch) {
            seed = MnemonicManager.getMnemonicFromJSON(EncryptedPreferencesManager.mnemonic!!)!!
        }

    }

    fun generateMnemonics() {
        //Should be created at the launch
        WalletManager.generateMnemonic()
        seed = MnemonicManager.getMnemonicFromJSON(EncryptedPreferencesManager.mnemonic!!)!!
    }
}
