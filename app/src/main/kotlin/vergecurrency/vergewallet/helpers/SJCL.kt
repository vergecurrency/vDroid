package vergecurrency.vergewallet.helpers

import android.content.Context

import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Array
import com.eclipsesource.v8.V8Object

import java.io.InputStream

import vergecurrency.vergewallet.Constants
import vergecurrency.vergewallet.helpers.utils.FileUtils

class SJCL(private val c: Context) {
    private var runtime: V8? = null
    private var sjcl: V8Object? = null

    fun init() {
        try {

            //get the script
            val `is` = c.assets.open(Constants.SJCL_FILE_PATH)
            val sjclFile = FileUtils.convertStreamToString(`is`)

            //init V8 Runtime
            runtime = V8.createV8Runtime()
            runtime!!.executeVoidScript(sjclFile)

            sjcl = runtime!!.getObject("sjcl")

        } catch (e: Exception) {
            e.printStackTrace()
            System.err.println("Can't do something..................................................")
        }

    }


    fun encrypt(key: IntArray, message: String, params: IntArray): String {

        val keyObj = V8Array(runtime!!)
        for (bitValue in key) {
            keyObj.push(bitValue)
        }

        val paramsObj = V8Object(runtime).add("ks", params[0]).add("iter", params[1])

        val paramsArray = V8Array(runtime!!).push(keyObj).push(message).push(paramsObj)
        val result = sjcl!!.executeStringFunction("encrypt", paramsArray)

        keyObj.close()
        paramsObj.close()
        paramsArray.close()

        return result
    }

    fun decrypt(key: IntArray, message: String): String {

        val keyObj = V8Array(runtime!!)
        for (bitValue in key) {
            keyObj.push(bitValue)
        }

        val result = sjcl!!.executeJSFunction("decrypt", keyObj, message.replace("\\", "")).toString()

        keyObj.close()

        return result
    }

    fun base64ToBits(encryptingKey: String): IntArray {
        val base64 = sjcl!!.getObject("codec").getObject("base64")

        val params = V8Array(runtime!!).push(encryptingKey)
        val result = base64.executeArrayFunction("toBits", params)

        base64.close()
        params.close()

        return result.getIntegers(0, result.length())
    }

    fun sha256Hash(data: String): IntArray {
        val sha256 = sjcl!!.getObject("hash").getObject("sha256")
        val params = V8Array(runtime!!).push(data)

        val result = sha256.executeArrayFunction("hash", params).getIntegers(0, 8)

        sha256.close()
        params.close()

        return result
    }

    fun hexFromBits(hash: IntArray): String {
        val hex = sjcl!!.getObject("codec").getObject("hex")
        val params = V8Array(runtime!!)
        for (`val` in hash) {
            params.push(`val`)
        }

        val result = hex.executeJSFunction("fromBits", params)

        hex.close()
        params.close()

        return result.toString()

    }

}
