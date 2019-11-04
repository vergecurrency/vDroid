package vergecurrency.vergewallet.viewmodel

import androidx.lifecycle.ViewModel

import vergecurrency.vergewallet.service.model.PreferencesManager

class PinPromptedViewModel : ViewModel() {


    val pin: String
        get() = PreferencesManager.pin!!
}
