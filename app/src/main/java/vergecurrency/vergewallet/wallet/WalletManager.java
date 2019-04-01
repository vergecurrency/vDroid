package vergecurrency.vergewallet.wallet;


import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import io.horizontalsystems.bitcoinkit.BitcoinKit;
import io.horizontalsystems.bitcoinkit.models.BlockInfo;
import io.horizontalsystems.bitcoinkit.models.TransactionInfo;
import io.horizontalsystems.hdwalletkit.Mnemonic;
import vergecurrency.vergewallet.service.model.MnemonicSeed;
import vergecurrency.vergewallet.service.model.PreferencesManager;

import static io.horizontalsystems.bitcoinkit.BitcoinKit.KitState;
import static io.horizontalsystems.bitcoinkit.BitcoinKit.Listener;
import static io.horizontalsystems.bitcoinkit.BitcoinKit.NetworkType;


public class WalletManager implements Listener {

	private static WalletManager INSTANCE = null;
	private MutableLiveData<Long> balance;
	private MnemonicSeed seed;
	private PreferencesManager pm;
	private BitcoinKit wallet;

	private WalletManager() {
		pm = PreferencesManager.getInstance();

	}

	public static WalletManager init() {
		if (INSTANCE != null) {
			throw new AssertionError("You already initialized an object of this type");
		}
		return INSTANCE = new WalletManager();
	}

	public static WalletManager getInstance() {
		if (INSTANCE == null) {
			throw new AssertionError("You haven't initialized an object of this type yet.");
		}
		return INSTANCE;
	}

	public void startWallet() throws Exception {
		NetworkType netType = NetworkType.MainNet;
		String[] seed = MnemonicSeed.getSeedFromJson(pm.getMnemonic());
		if (seed != null) {
			wallet = new BitcoinKit((List<String>) Arrays.asList(seed), netType, null, 10, true, 1);
			wallet.setListener(this);
			String networkName = netType.name();
			balance.setValue(wallet.getBalance());

			wallet.start();
		} else {
			//I don't know, I'll see how to handle this.
			throw new Exception();
		}
	}




	public void generateSeed() {

		seed = new MnemonicSeed();

		seed.setSeed(new Mnemonic().generate(Mnemonic.Strength.Default).toArray(new String[0]));

		pm.setMnemonic(seed.getSeedAsJson());
	}


	@Override
	public void onBalanceUpdate(@NotNull BitcoinKit bitcoinKit, long l) {

	}

	@Override
	public void onKitStateUpdate(@NotNull BitcoinKit bitcoinKit, @NotNull KitState kitState) {

	}

	@Override
	public void onLastBlockInfoUpdate(@NotNull BitcoinKit bitcoinKit, @NotNull BlockInfo blockInfo) {

	}

	@Override
	public void onTransactionsDelete(@NotNull List<String> list) {

	}

	@Override
	public void onTransactionsUpdate(@NotNull BitcoinKit bitcoinKit, @NotNull List<TransactionInfo> list, @NotNull List<TransactionInfo> list1) {

	}

	public void setBalance(MutableLiveData<Long> balance) {
		this.balance = balance;
	}

	public MutableLiveData<Long> getBalance() {
		balance.setValue(wallet.getBalance());
		return balance;
	}
//store wallet


	//fuck wallet
}
