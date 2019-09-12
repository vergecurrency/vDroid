package vergecurrency.vergewallet.service.repository;

import java.net.URI;
import java.net.URISyntaxException;

import vergecurrency.vergewallet.Constants;

public class BitcoreClient {


	public void scanAddresses() {
		postRequest("/v1/addresses/scan", null, null);
	}

	/**
	 *
	 */
	public void createAddresses() {
		postRequest("/v4/addresses", null, null);
	}

	/**
	 *
	 * @param walletName
	 * @param copayerName
	 * @param m
	 * @param n
	 * @param options
	 * @return
	 */
	public String createWallet(String walletName, String copayerName, int m, int n, String options) {
		postRequest("/v2/wallets", null,null);

		return "";
	}

	public void getBalance() {
		getRequest("/v1/balance",null,null);
	}

	public void getMainAddresses() {
		getRequest("/v1/addresses/",null,null);
	}

	public void getTxHistory() {
		getRequest("/v1/txhistory/?includeExtendedInfo=1",null,null);
	}

	public void getUnspentOutputs(){
		getRequest("/v1/utxos/",null,null);
	}

	public void getSendMaxInfo() {
		getRequest("/v1/sendmaxinfo",null,null);
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
