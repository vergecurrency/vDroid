package vergecurrency.vergewallet.service.repository;

import android.content.Context;
import android.provider.Telephony;

import java.net.URI;
import java.net.URISyntaxException;

import io.horizontalsystems.bitcoinkit.models.Address;
import kotlin.NotImplementedError;
import vergecurrency.vergewallet.Constants;
import vergecurrency.vergewallet.helpers.SJCL;
import vergecurrency.vergewallet.service.model.vws.AddressInfo;

public class BitcoreClient {

	private SJCL sjcl;

	private String baseUrl;

	public BitcoreClient(Context c){
		sjcl = new SJCL(c);
		baseUrl = "";

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

	public void scanAddresses() {
		postRequest("/v1/addresses/scan", null, null);
	}

	/**
	 *
	 */
	public void createAddresses() {
		postRequest("/v4/addresses", null, null);
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


	//------------------------------------------------
	// SJCL and various Helper methods
	//------------------------------------------------

	private String getSignature() {
		return "";
	}

	private String signMessage() {
		return "";
	}

	private String encryptMessage(String plaintext, String encryptingKey) {
		String key = sjcl.base64ToBits(encryptingKey);
		return sjcl.encrypt(key,plaintext,new int[]{128, 1});
	}

	public String decryptMessage(String cyphertext, String encryptingKey) {
		String key = sjcl.base64ToBits(encryptingKey);
		return sjcl.decrypt(key,cyphertext);
	}

	//------------------------------------------------
	// HTTP Request helpers
	//------------------------------------------------


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

	class invalidDeriverException extends Exception {
		public invalidDeriverException(String value) {
			super(value);
		}
	}

	class invalidMessageDataException extends Exception {
		public invalidMessageDataException(String message) {
			super(message);
		}
	}

	class invalidWidHexException extends Exception{
		public invalidWidHexException(String id) {
			super(id);
		}
	}

	class invalidAddressReceivedException extends Exception {
		public invalidAddressReceivedException(AddressInfo addressInfo) {
			super(addressInfo.getAddress());
		}

	}
}
