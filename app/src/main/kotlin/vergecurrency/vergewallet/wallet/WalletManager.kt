package vergecurrency.vergewallet.wallet

import androidx.lifecycle.MutableLiveData
import io.horizontalsystems.bitcoinkit.BitcoinKit
import io.horizontalsystems.bitcoinkit.BitcoinKit.*
import io.horizontalsystems.bitcoinkit.models.BlockInfo
import io.horizontalsystems.bitcoinkit.models.TransactionInfo
import io.realm.Realm
import io.realm.RealmConfiguration
import vergecurrency.vergewallet.VergeWalletApplication
import vergecurrency.vergewallet.helpers.utils.WalletDataIdentifierUtils
import vergecurrency.vergewallet.service.model.EncryptedPreferencesManager
import vergecurrency.vergewallet.service.model.EncryptedPreferencesManager.Companion.mnemonic
import vergecurrency.vergewallet.service.model.EncryptedPreferencesManager.Companion.walletName
import vergecurrency.vergewallet.service.model.MnemonicManager
import vergecurrency.vergewallet.service.model.VDroidRealmModule
import java.util.*
import kotlin.reflect.jvm.internal.impl.protobuf.UnmodifiableLazyStringList

class WalletManager private constructor() : Listener {

    init {
        balance = MutableLiveData()
    }

    fun setBalance(balance: MutableLiveData<Long>) {
        WalletManager.balance = balance
    }

    override fun onBalanceUpdate(bitcoinKit: BitcoinKit, balance: Long) {

    }

    override fun onKitStateUpdate(bitcoinKit: BitcoinKit, state: KitState) {

    }

    override fun onLastBlockInfoUpdate(bitcoinKit: BitcoinKit, blockInfo: BlockInfo) {

    }

    override fun onTransactionsDelete(hashes: List<String>) {

    }

    override fun onTransactionsUpdate(bitcoinKit: BitcoinKit, inserted: List<TransactionInfo>, updated: List<TransactionInfo>) {

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

        @Throws(Exception::class)
        fun startWallet() {
            val netType = NetworkType.MainNet
            val mnemonic: Array<String> = MnemonicManager.getMnemonicFromJSON(mnemonic!!)!!.map { a -> String(a) }.toTypedArray()
            wallet = BitcoinKit(Arrays.asList(*mnemonic), String(EncryptedPreferencesManager.passphrase!!), netType, "wallet", 10, true, 1)
            wallet!!.listener = INSTANCE
            //val networkName = netType.name

            wallet!!.start()

            balance!!.setValue(wallet!!.balance)
        }

        val receiveAddress: String
            get() = wallet!!.receiveAddress()

        fun getTransactions() {}

        fun generateMnemonic() {

            val mnemonicManager = MnemonicManager()
            mnemonicManager.mnemonic = io.horizontalsystems.hdwalletkit.Mnemonic().generate(io.horizontalsystems.hdwalletkit.Mnemonic.Strength.Default).map { m -> m.toCharArray() }.toTypedArray()
            // TODO
            // mnemonic = mnemonicManager.mnemonicAsJSON
        }


        fun getBalance(): MutableLiveData<Long> {
            try {
                balance!!.setValue(wallet!!.balance)
            } catch (e: java.lang.Exception) {
                balance!!.setValue(0L)
            }
            return balance as MutableLiveData<Long>
        }
    }
}
