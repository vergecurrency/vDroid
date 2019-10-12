package vergecurrency.vergewallet.service.repository;

import android.content.Context;
import android.provider.Telephony;

import java.net.URI;
import java.net.URISyntaxException;

import io.horizontalsystems.bitcoinkit.models.Address;
import vergecurrency.vergewallet.Constants;
import vergecurrency.vergewallet.helpers.SJCL;

public class BitcoreClient {

	private SJCL sjcl;

	public BitcoreClient(Context c){
		sjcl = new SJCL(c);

	}

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
	// Thanks to the mad verbosity of Java I can not have an enum of exceptions so
	//INNER CLASSES BABY. HUNG ME SOMEWHERE.

	class addressToScriptException extends Exception {
		public addressToScriptException(Address address){
			super(address.string);

		}
	}

	class invalidDeriver extends Exception {
		public invalidDeriver(String value) {
			super(value);
		}
	}

	class invalidMessageData extends Exception {
		public invalidMessageData(String message) {
			super(message);
		}
	}

	class invalidWidHex extends Exception{
		public invalidWidHex(String id) {
			super(id);
		}
	}

	class invalidAddressReceived extends Exception {
		public invalidAddressReceived(Address)
	}
}
