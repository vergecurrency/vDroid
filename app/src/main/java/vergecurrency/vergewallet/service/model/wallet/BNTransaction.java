package vergecurrency.vergewallet.service.model.wallet;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.crypto.tink.subtle.Hex;

import io.horizontalsystems.bitcoinkit.managers.BCoinApi;
import vergecurrency.vergewallet.helpers.utils.DataUtils;


public class BNTransaction {

	private String chain;
	private String network;
	private boolean coinbase;
	private int mintIndex;
	private String spentTxid;
	private String mintTxid;
	private long mintHeight;
	private long spentHeight;
	private String address;
	private String script;
	private long value;
	private long confirmations;


	public void asUnspentTransaction() throws InvalidScriptPubKeyHexException, InvalidTxIdHexException {
		byte[] lockingScript, txid;
		try {
			lockingScript = Hex.decode(script);
		} catch (Exception e) {
			throw new InvalidScriptPubKeyHexException(script);
		}
		try {
			 txid = Hex.decode(mintTxid);
		} catch (Exception e) {
			throw new InvalidTxIdHexException(mintTxid);
		}

		//BCoinApi.TransactionOutputItem transactionOutputItem = new BCoinApi.TransactionOutputItem(value, lockingScript)
		byte[] txHash = DataUtils.reverse(txid);

	}




	class InvalidScriptPubKeyHexException extends Exception {
		public InvalidScriptPubKeyHexException (String hex) {
			super(hex);
		}
	}

	class InvalidTxIdHexException extends Exception {
		public InvalidTxIdHexException (String hex) {
			super(hex);

		}
	}

}
