package vergecurrency.vergewallet.viewmodel

import androidx.lifecycle.ViewModel
import vergecurrency.vergewallet.service.model.EncryptedPreferencesManager

class PinPromptedViewModel : ViewModel() {


    val pin: CharArray
        get() = EncryptedPreferencesManager.pin!!
}
