package vergecurrency.vergewallet.service.model

import com.google.gson.Gson

class MnemonicManager {


    var mnemonic: Array<String>? = null

    val mnemonicAsJSON: String
        get() = Gson().toJson(this.mnemonic)

    companion object {

        fun getMnemonicFromJSON(mnemonic: String): Array<String>? {
            return Gson().fromJson(mnemonic, Array<String>::class.java)
        }
    }
}
