package vergecurrency.vergewallet.viewmodel

import androidx.lifecycle.ViewModel
import vergecurrency.vergewallet.Constants
import vergecurrency.vergewallet.service.model.network.layers.TorLayerGateway
import vergecurrency.vergewallet.service.repository.ApifyService
import vergecurrency.vergewallet.service.repository.GeocodingService

class TorSettingsViewModel : ViewModel() {

    val ipAddress: String
    val coordinates: String

    init {
        val tlg = TorLayerGateway

        ipAddress = ApifyService.requestIP()
        coordinates = GeocodingService.readCoordinates(ipAddress)
    }
}
