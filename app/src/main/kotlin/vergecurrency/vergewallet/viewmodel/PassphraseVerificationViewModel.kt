package vergecurrency.vergewallet.viewmodel

import androidx.lifecycle.ViewModel
import vergecurrency.vergewallet.service.model.PreferencesManager


class PassphraseVerificationViewModel : ViewModel() {

    /*fun setFirstLaunch(isFirstLaunch: Boolean) {
        PreferencesManager.isFirstLaunch = isFirstLaunch
    }*/

    fun setPassphrase(passphrase: String) {
        PreferencesManager.passphrase = passphrase
    }
}
