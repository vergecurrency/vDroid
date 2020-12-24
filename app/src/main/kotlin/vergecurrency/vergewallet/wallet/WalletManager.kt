package vergecurrency.vergewallet.wallet

import android.content.Context
import androidx.lifecycle.MutableLiveData
import io.horizontalsystems.bitcoincore.BitcoinCore
import io.horizontalsystems.bitcoincore.core.Bip
import io.horizontalsystems.bitcoinkit.BitcoinKit
import io.horizontalsystems.bitcoinkit.BitcoinKit.*
import vergecurrency.vergewallet.service.model.EncryptedPreferencesManager
import vergecurrency.vergewallet.service.model.EncryptedPreferencesManager.Companion.mnemonic
import vergecurrency.vergewallet.service.model.MnemonicManager

class WalletManager private constructor() : Listener {

    init {
        balance = MutableLiveData()
    }

    fun setBalance(balance: MutableLiveData<Long>) {
        WalletManager.balance = balance
    }


    override fun onTransactionsDelete(hashes: List<String>) {

    }

    companion object {

        private var INSTANCE: WalletManager? = null
        private var balance: MutableLiveData<Long>? = null
        private var wallet: BitcoinKit? = null

        fun init() {
            if (INSTANCE != null) {
                throw AssertionError("You already initialized an object of this type")
            }
            INSTANCE = WalletManager()
        }

        val instance: WalletManager
            get() {
                if (INSTANCE == null) {
                    throw AssertionError("You haven't initialized an object of this type yet.")
                }
                return INSTANCE as WalletManager
            }


        /*
         constructor(
            context: Context,
            words: List<String>,
            salt : String,
            walletId: String,
            networkType: NetworkType = NetworkType.MainNet,
            peerSize: Int = 10,
            syncMode: BitcoinCore.SyncMode = BitcoinCore.SyncMode.Api(),
            confirmationsThreshold: Int = 6,
            bip: Bip = Bip.BIP44
    ) : this(context, Mnemonic().toSeedWithPassphrase(words, salt), walletId, networkType, peerSize, syncMode, confirmationsThreshold, bip)
         */
        @Throws(Exception::class)
        fun startWallet(context : Context) {
            val netType = NetworkType.MainNet
            val mnemonic = MnemonicManager.getMnemonicFromJSON(mnemonic!!)
            if (mnemonic != null) {
                wallet = BitcoinKit(context, listOf(*mnemonic), EncryptedPreferencesManager.passphrase!!, "wallet", netType, 10, BitcoinCore.SyncMode.NewWallet(), 3, Bip.BIP44)
                wallet!!.listener = INSTANCE
                //val networkName = netType.name

                wallet!!.start()

                balance!!.setValue(wallet!!.balance.spendable)

            } else {
                //I don't know, I'll see how to handle this.
                throw Exception()
            }
        }


        val receiveAddress: String
            get() = wallet!!.receiveAddress()

        fun getTransactions() {}

        fun generateMnemonic() {

            val mnemonicManager = MnemonicManager()
            mnemonicManager.mnemonic = io.horizontalsystems.hdwalletkit.Mnemonic().generate(io.horizontalsystems.hdwalletkit.Mnemonic.Strength.Default).toTypedArray()
            mnemonic = mnemonicManager.mnemonicAsJSON
        }

        fun getBalance(): MutableLiveData<Long> {
            try {
                balance!!.setValue(wallet!!.balance.spendable)
            } catch (e: java.lang.Exception) {
                balance!!.setValue(0L)
            }
            return balance as MutableLiveData<Long>
        }
    }
}
