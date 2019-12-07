package vergecurrency.vergewallet.helpers.utils

import io.horizontalsystems.bitcoinkit.models.TransactionOutput

object ArrayUtils {

    fun filterByIndices(indices : List<Int>, target : ArrayList<TransactionOutput>) : ArrayList<TransactionOutput> {
            var result : ArrayList<TransactionOutput> = ArrayList()
        for (index in indices) {
            if (target.indices.contains(index)) {
                result.add(target.get(index))
            }
        }
        return result

    }
}