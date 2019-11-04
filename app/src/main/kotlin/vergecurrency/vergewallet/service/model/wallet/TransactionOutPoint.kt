package vergecurrency.vergewallet.service.model.wallet

import java.math.BigInteger
import java.nio.ByteBuffer
import java.util.Arrays

class TransactionOutPoint(var hash: ByteArray?, var index: Int) {

    fun serialize(): ByteArray {
        val indexBA = BigInteger.valueOf(index.toLong()).toByteArray()
        val result = ByteArray(indexBA.size + hash!!.size)
        System.arraycopy(hash!!, 0, result, 0, hash!!.size)
        System.arraycopy(indexBA, 0, result, hash!!.size, indexBA.size)
        return result
    }

    companion object {

        fun deserialize(data: ByteArray): TransactionOutPoint {
            return TransactionOutPoint(Arrays.copyOfRange(data, 0, 31), ByteBuffer.wrap(Arrays.copyOfRange(data, 32, 35)).int)
        }
    }
}
