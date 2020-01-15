package vergecurrency.vergewallet.viewmodel

import androidx.lifecycle.ViewModel
import vergecurrency.vergewallet.service.model.PreferencesManager


class PermissionsViewModel : ViewModel() {

    fun setFirstLaunch(isFirstLaunch: Boolean) {
        PreferencesManager.isFirstLaunch = isFirstLaunch
    }

}
