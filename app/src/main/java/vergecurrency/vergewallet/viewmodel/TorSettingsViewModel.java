package vergecurrency.vergewallet.viewmodel;

import androidx.lifecycle.ViewModel;
import vergecurrency.vergewallet.Constants;
import vergecurrency.vergewallet.service.model.network.layers.TorLayerGateway;
import vergecurrency.vergewallet.service.repository.ApifyService;
import vergecurrency.vergewallet.service.repository.GeocodingService;

public class TorSettingsViewModel extends ViewModel {

	//TODO : The services should handle the queries itself...

	private String IP;
	private String coordinates;


	public TorSettingsViewModel() {

		ApifyService adr = new ApifyService();
		GeocodingService idr = new GeocodingService();
		//This should not be there and will disappear SoonTM
		TorLayerGateway tlg = TorLayerGateway.getInstance();

		IP = adr.readIP(tlg.retrieveDataFromService(Constants.IP_RETRIEVAL_ENDPOINT));
		coordinates = idr.readCoordinates(tlg.retrieveDataFromService(String.format(Constants.IP_DATA_ENDPOINT, IP)));
	}

	public String getCoordinates() {
		return coordinates;
	}

	public String getIPAddress() {
		return IP;
	}
}
