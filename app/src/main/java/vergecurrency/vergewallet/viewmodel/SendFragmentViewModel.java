package vergecurrency.vergewallet.viewmodel;

import androidx.lifecycle.ViewModel;
import vergecurrency.vergewallet.wallet.WalletManager;

public class SendFragmentViewModel extends ViewModel {

	private Long balance;

	public SendFragmentViewModel() {
		balance = WalletManager.getBalance().getValue();
	}


	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}
}
