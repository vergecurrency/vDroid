package vergecurrency.vergewallet.helpers.utils

import java.io.EOFException
import java.io.IOException
import java.io.InputStream
import java.math.BigDecimal
import java.math.RoundingMode

object MathUtils {

    @Throws(IOException::class)
    fun readUnsignedInt(`in`: InputStream): Long {
        val ch1 = `in`.read()
        val ch2 = `in`.read()
        val ch3 = `in`.read()
        val ch4 = `in`.read()
        if (ch1 or ch2 or ch3 or ch4 < 0) {
            throw EOFException()
        }
        val ln4 = ch4 and 0x00000000ffffffffL.toInt()
        return (ln4 shl 24) + (ch3 shl 16).toLong() + (ch2 shl 8).toLong() + (ch1 shl 0).toLong()
    }


    fun round(value: Double, places: Int): Double {
        require(places >= 0)

        var bd = BigDecimal(value)
        bd = bd.setScale(places, RoundingMode.HALF_UP)
        return bd.toDouble()
    }

    fun getRandomNumber(max: Int): Int {
        return (Math.random() * max).toInt()
    }

}
