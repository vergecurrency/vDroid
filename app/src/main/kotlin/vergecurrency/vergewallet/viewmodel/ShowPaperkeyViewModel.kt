package vergecurrency.vergewallet.viewmodel

import androidx.lifecycle.ViewModel

import vergecurrency.vergewallet.service.model.MnemonicManager

import vergecurrency.vergewallet.service.model.PreferencesManager.Companion.mnemonic

class ShowPaperkeyViewModel : ViewModel() {
    var seed: Array<String>

    val seedAsText: String
        get() {
            val result = StringBuilder()
            for (s in seed) {
                result.append(s).append(" ")
            }
            return result.toString()
        }

    init {
        seed = MnemonicManager.getMnemonicFromJSON(mnemonic!!)!!

    }

}
