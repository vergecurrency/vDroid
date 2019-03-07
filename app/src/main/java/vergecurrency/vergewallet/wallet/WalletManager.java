package vergecurrency.vergewallet.wallet;


import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import io.horizontalsystems.bitcoinkit.BitcoinKit;
import io.horizontalsystems.bitcoinkit.models.BlockInfo;
import io.horizontalsystems.bitcoinkit.models.TransactionInfo;
import io.horizontalsystems.hdwalletkit.Mnemonic;

import static io.horizontalsystems.bitcoinkit.BitcoinKit.*;


public class WalletManager implements Listener {

	private String networkName;
	private MutableLiveData<Long> balance;

	public WalletManager() {

	}


	public void startWallet(String seed) {
		NetworkType netType = NetworkType.MainNet;

		BitcoinKit wallet = new BitcoinKit((List<String>) Arrays.asList(seed), netType,null,10,true,1);
		wallet.setListener(this);
		networkName = netType.name();
		balance.setValue(wallet.getBalance());


		wallet.start();

	}


	public String[] generateSeed() {

		String[] seed = new Mnemonic().generate(Mnemonic.Strength.Default).toArray(new String[0]);

		return seed;
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

	//store wallet


	//fuck wallet
}
