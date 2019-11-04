package vergecurrency.vergewallet.viewmodel

import androidx.lifecycle.ViewModel

import vergecurrency.vergewallet.Constants
import vergecurrency.vergewallet.service.model.PreferencesManager

class ServiceURLViewModel : ViewModel() {

    val currentServiceURL: String
        get() = PreferencesManager.walletServiceUrl!!

    fun setNewServiceURL(serviceURL: String) {
        PreferencesManager.walletServiceUrl = serviceURL
    }

    fun setDefaultServiceURL() {
        setNewServiceURL(Constants.VWS_ENDPOINT)
    }
}
