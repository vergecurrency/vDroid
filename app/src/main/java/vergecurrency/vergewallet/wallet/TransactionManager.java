package vergecurrency.vergewallet.wallet;

import vergecurrency.vergewallet.service.repository.db.TransactionRepository;

public class TransactionManager {

	private WalletManager wm;
	private TransactionRepository tp;

	public TransactionManager(WalletManager wm, TransactionRepository tp){
		this.tp = tp;
		this.wm = wm;
	}

	public boolean hasTransactions() {
		//sql query address like '%%' to get every result
		return !tp.getByAddress("%%").isEmpty();
	}

}
