package vergecurrency.vergewallet.viewmodel

import androidx.lifecycle.ViewModel
import vergecurrency.vergewallet.service.model.EncryptedPreferencesManager


class PassphraseVerificationViewModel : ViewModel() {

    /*fun setFirstLaunch(isFirstLaunch: Boolean) {
        PreferencesManager.isFirstLaunch = isFirstLaunch
    }*/

    fun setPassphrase(passphrase: ByteArray) {
        EncryptedPreferencesManager.passphrase = passphrase
    }
}
