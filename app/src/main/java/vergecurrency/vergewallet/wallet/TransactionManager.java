package vergecurrency.vergewallet.wallet;

import vergecurrency.vergewallet.service.repository.db.TransactionRepository;

public class TransactionManager {
	private TransactionRepository tp;

	public TransactionManager(TransactionRepository tp){
		this.tp = tp;
	}

	public boolean hasTransactions() {
		//sql query address like '%%' to get every result
		return !tp.getByAddress("%%").isEmpty();
	}

}
