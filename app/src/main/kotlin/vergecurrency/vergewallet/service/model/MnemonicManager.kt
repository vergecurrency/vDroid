package vergecurrency.vergewallet.service.model

import com.google.gson.Gson

class MnemonicManager {

    companion object {

        fun getMnemonicFromJSON(mnemonic: ByteArray): Array<ByteArray>? {
            return Gson().fromJson(String(mnemonic), Array<ByteArray>::class.java)
        }
    }
}
