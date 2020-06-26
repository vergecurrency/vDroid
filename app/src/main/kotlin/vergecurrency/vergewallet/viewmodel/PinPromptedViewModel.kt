package vergecurrency.vergewallet.viewmodel

import androidx.lifecycle.ViewModel
import vergecurrency.vergewallet.service.model.EncryptedPreferencesManager

class PinPromptedViewModel : ViewModel() {


    val pin: ByteArray
        get() = EncryptedPreferencesManager.pin!!
}
