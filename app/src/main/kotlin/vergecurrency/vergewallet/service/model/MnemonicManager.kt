package vergecurrency.vergewallet.service.model

import com.google.gson.Gson

class MnemonicManager {


    var mnemonic: Array<CharArray>? = null

    val mnemonicAsJSON: CharArray
        get() = Gson().toJson(this.mnemonic).toCharArray()

    companion object {

        fun getMnemonicFromJSON(mnemonic: CharArray): Array<CharArray>? {
            return Gson().fromJson(String(mnemonic), Array<CharArray>::class.java)
        }
    }
}
