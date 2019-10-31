package vergecurrency.vergewallet.wallet;

import android.content.Context;

import com.google.crypto.tink.subtle.Hex;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyFactory;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

import io.horizontalsystems.bitcoinkit.crypto.Base58;
import io.horizontalsystems.bitcoinkit.models.Address;
import io.horizontalsystems.bitcoinkit.models.LegacyAddress;
import io.horizontalsystems.bitcoinkit.utils.HashUtils;
import io.horizontalsystems.hdwalletkit.HDKey;
import vergecurrency.vergewallet.Constants;
import vergecurrency.vergewallet.helpers.SJCL;
import vergecurrency.vergewallet.helpers.utils.ValidationUtils;
import vergecurrency.vergewallet.service.model.wallet.AddressInfo;

public class WalletClient {

	private SJCL sjcl;

	private String baseUrl;

	public WalletClient(Context c) {
		sjcl = new SJCL(c);
		baseUrl = "";

	}

	public String createWallet(String walletName, String copayerName, int m, int n, String options) {
		postRequest("/v2/wallets", null, null);

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
		getRequest("/v1/balance", null, null);
	}

	public void getMainAddresses() {
		getRequest("/v1/addresses/", null, null);
	}

	public void getTxHistory() {
		getRequest("/v1/txhistory/?includeExtendedInfo=1", null, null);
	}

	public void getUnspentOutputs() {
		getRequest("/v1/utxos/", null, null);
	}

	public void getSendMaxInfo() {
		getRequest("/v1/sendmaxinfo", null, null);
	}


	//------------------------------------------------
	// SJCL and various Helper methods
	//------------------------------------------------

	private String getSignature() {
		return "";
	}

	private String signMessage(String message, HDKey privKey) throws InvalidMessageDataException {

		String result;

		if (ValidationUtils.isValidUTF8(message)) {

			try {
				Signature sig = Signature.getInstance("SHA256withECDSA");
				sig.initSign(KeyFactory.getInstance("EC").generatePrivate(new PKCS8EncodedKeySpec(privKey.getPrivKeyBytes())));
				sig.update(HashUtils.doubleSha256(message.getBytes()));
				result = new String(sig.sign());

			} catch (Exception e) {
				e.printStackTrace();
				result = "SignatureException";
			}

		} else {
			throw new InvalidMessageDataException(message);
		}

		return result;
	}

	private String buildSecret(String walletId) throws InvalidWidHexException {
		try {
			byte[] widHex = Hex.decode(walletId.replace("-", ""));
			String widBase58 = String.format("%1$-" + Base58.encode(widHex).length() + "s", Base58.encode(widHex)).replace(" ", "0");

			return "\\(" + widBase58 + ")\\(credentials.privateKey.toWIF()" + ")Lxvg";

		} catch (Exception e) {
			throw new InvalidWidHexException(walletId);
		}
	}

	private String getUnsignedTx(String something) {
		//LegacyAddress add = new LegacyAddress()


		return "";
	}

	private String encryptMessage(String plaintext, String encryptingKey) {
		int[] key = sjcl.base64ToBits(encryptingKey);
		return sjcl.encrypt(key, plaintext, new int[]{128, 1});
	}

	public String decryptMessage(String cyphertext, String encryptingKey) {
		int[] key = sjcl.base64ToBits(encryptingKey);
		return sjcl.decrypt(key, cyphertext);
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

	class AddressToScriptException extends Exception {
		public AddressToScriptException(Address address) {
			super(address.string);
		}
	}

	class InvalidDeriverException extends Exception {
		public InvalidDeriverException(String value) {
			super(value);
		}
	}

	class InvalidMessageDataException extends Exception {
		public InvalidMessageDataException(String message) {
			super(message);
		}
	}

	class InvalidWidHexException extends Exception {
		public InvalidWidHexException(String id) {
			super(id);
		}
	}

	class InvalidAddressReceivedException extends Exception {
		public InvalidAddressReceivedException(AddressInfo addressInfo) {
			super(addressInfo.getAddress());
		}

	}
}
