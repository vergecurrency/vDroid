package vergecurrency.vergewallet.viewmodel

import androidx.lifecycle.ViewModel
import vergecurrency.vergewallet.service.model.EncryptedPreferencesManager.Companion.mnemonic
import vergecurrency.vergewallet.service.model.MnemonicManager

class ShowPaperkeyViewModel : ViewModel() {
    var seed: Array<ByteArray>

    val seedAsText: String
        get() {
            val result = StringBuilder()
            for (s in seed) {
                result.append(String(s)).append(" ")
            }
            return String(result)
        }

    init {
        seed = MnemonicManager.getMnemonicFromJSON(mnemonic!!)!!

    }

}
