package vergecurrency.vergewallet.viewmodel

import androidx.lifecycle.ViewModel

import vergecurrency.vergewallet.service.model.PreferencesManager

class PinVerificationViewModel : ViewModel() {


    fun setPin(pin: String) {
        PreferencesManager.pin = pin
    }
}
