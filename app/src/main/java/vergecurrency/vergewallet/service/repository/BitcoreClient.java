package vergecurrency.vergewallet.service.repository;

import java.net.URI;
import java.net.URISyntaxException;

import vergecurrency.vergewallet.Constants;

public class BitcoreClient {


	public void scanAddresses() {
		postRequest("/v1/addresses/scan", null, null);
	}

	public void createAddresses() {
		postRequest("/v4/addresses", null, null);
	}

	public void getBalance() {
		getRequest("/v1/balance",null,null);
	}


	public void postRequest(String url, String jsonArgs, String escape) {
		try {
			URI uri = new URI(String.format("%s%s", Constants.VWS_ENDPOINT, url));
			//...

		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}

	public void getRequest(String url, String jsonArgs, String escape) {
		try {
			URI uri = new URI(String.format("%s%s", Constants.VWS_ENDPOINT, url));
			//...

		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}
}
