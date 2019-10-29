package vergecurrency.vergewallet.service.model.wallet;

import com.google.crypto.tink.subtle.Hex;

import io.horizontalsystems.bitcoinkit.io.BitcoinInput;
import io.horizontalsystems.bitcoinkit.managers.UnspentOutputProvider;
import io.horizontalsystems.bitcoinkit.managers.UnspentOutputSelector;
import io.horizontalsystems.bitcoinkit.models.TransactionInput;
import io.horizontalsystems.bitcoinkit.models.TransactionOutput;
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



	//This can possibly be a big chunk of shit code. to test first priority.
	public UnspentTransaction asUnspentTransaction() throws InvalidScriptPubKeyHexException, InvalidTxIdHexException {
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

		TransactionOutput transactionOutput = new TransactionOutput();
		transactionOutput.setValue(value);
		transactionOutput.setLockingScript(lockingScript);
		byte[] txHash = DataUtils.reverse(txid);
		TransactionOutPoint transactionOutPoint = new TransactionOutPoint(txHash,mintIndex);

		return new UnspentTransaction(transactionOutput,transactionOutPoint);
	}

	public TransactionInput asInputTransaction() throws InvalidTxIdHexException {
		byte[] txid;
		try {
			txid = Hex.decode(mintTxid);
		} catch (Exception e) {
			throw new InvalidTxIdHexException(mintTxid);
		}
		byte[] txHash = DataUtils.reverse(txid);
		TransactionOutPoint transactionOutPoint = new TransactionOutPoint(txHash,mintIndex);

		//This is probs bullshit but the BitcoinKit on Android and Swift don't correspond on this one so let's see...
		return new TransactionInput(new BitcoinInput(transactionOutPoint.serialize()));

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
