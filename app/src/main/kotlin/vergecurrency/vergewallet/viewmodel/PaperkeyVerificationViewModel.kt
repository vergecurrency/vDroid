package vergecurrency.vergewallet.viewmodel

import androidx.lifecycle.ViewModel
import vergecurrency.vergewallet.service.model.EncryptedPreferencesManager

import vergecurrency.vergewallet.service.model.MnemonicManager
import vergecurrency.vergewallet.service.model.PreferencesManager

class PaperkeyVerificationViewModel : ViewModel() {
    var seed: Array<String> = MnemonicManager.getMnemonicFromJSON(EncryptedPreferencesManager.mnemonic!!)!!

    fun setFirstLaunch(isFirstLaunch: Boolean) {
        PreferencesManager.isFirstLaunch = isFirstLaunch
    }
}
