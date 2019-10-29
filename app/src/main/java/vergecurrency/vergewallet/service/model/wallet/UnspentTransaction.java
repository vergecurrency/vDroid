package vergecurrency.vergewallet.service.model.wallet;

import io.horizontalsystems.bitcoinkit.models.TransactionOutput;

public class UnspentTransaction {

	private TransactionOutput output;
	private TransactionOutPoint outpoint;

	public UnspentTransaction(TransactionOutput output, TransactionOutPoint outpoint) {
		this.output = output;
		this.outpoint = outpoint;
	}
}
