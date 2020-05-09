package vergecurrency.vergewallet.wallet

import vergecurrency.vergewallet.service.repository.db.TransactionDAO

class TransactionManager(private val transactionDAO: TransactionDAO) {

    fun hasTransactions(): Boolean {
        return transactionDAO.hasRecords();
    }

}
