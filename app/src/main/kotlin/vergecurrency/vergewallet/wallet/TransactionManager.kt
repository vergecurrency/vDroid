package vergecurrency.vergewallet.wallet

import vergecurrency.vergewallet.service.repository.db.TransactionRepository

class TransactionManager(private val tp: TransactionRepository) {

    fun hasTransactions(): Boolean {
        //sql query address like '%%' to get every result
        return !tp.getByAddress("%%").isEmpty()
    }

}
