package vergecurrency.vergewallet.viewmodel

import androidx.lifecycle.ViewModel
import vergecurrency.vergewallet.Constants
import vergecurrency.vergewallet.service.model.network.layers.TorLayerGateway
import vergecurrency.vergewallet.service.repository.ApifyService
import vergecurrency.vergewallet.service.repository.GeocodingService

class TorSettingsViewModel : ViewModel() {

    //TODO : The services should handle the queries itself...

    //val ipAddress: String
    //val coordinates: String

    //init {

        //val adr = ApifyService()
        //val idr = GeocodingService()
        //This should not be there and will disappear SoonTM
        //val tlg = TorLayerGateway

        //ipAddress = adr.readIP(tlg.retrieveDataFromService(Constants.IP_RETRIEVAL_ENDPOINT))
        //coordinates = idr.readCoordinates(tlg.retrieveDataFromService(String.format(Constants.IP_DATA_ENDPOINT, ipAddress)))
    //}
}
