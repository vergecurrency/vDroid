package vergecurrency.vergewallet.viewmodel

import androidx.lifecycle.ViewModel
import vergecurrency.vergewallet.Constants
import vergecurrency.vergewallet.service.model.network.layers.TorLayerGateway
import vergecurrency.vergewallet.service.repository.ApifyService
import vergecurrency.vergewallet.service.repository.GeocodingService

class TorSettingsViewModel : ViewModel() {

    var ipAddress: String
    var coordinates: String

    init {
        ipAddress = ApifyService.requestIP()
        coordinates = GeocodingService.readCoordinates(ipAddress)
    }

    fun updateAndReturnIpAddress() :String  {
        ipAddress = ApifyService.requestIP()
        return ipAddress
    }

    fun updateAndReturnCoordinates() : String {
        coordinates = GeocodingService.readCoordinates(ipAddress)
        return coordinates
    }
}
