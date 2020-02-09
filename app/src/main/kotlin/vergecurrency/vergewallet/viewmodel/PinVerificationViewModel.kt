package vergecurrency.vergewallet.viewmodel

import androidx.lifecycle.ViewModel
import vergecurrency.vergewallet.service.model.EncryptedPreferencesManager

class PinVerificationViewModel : ViewModel() {


    fun setPin(pin: String) {
        EncryptedPreferencesManager.pin = pin
    }
}
