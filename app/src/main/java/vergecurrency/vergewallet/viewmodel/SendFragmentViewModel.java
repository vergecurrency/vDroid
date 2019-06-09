package vergecurrency.vergewallet.viewmodel;

import androidx.lifecycle.ViewModel;
import vergecurrency.vergewallet.wallet.WalletManager;

public class SendFragmentViewModel extends ViewModel {

	private Long balance;
	private WalletManager wm;

	public SendFragmentViewModel() {

		wm = WalletManager.getInstance();

		balance = wm.getBalance().getValue();
	}


	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}
}
