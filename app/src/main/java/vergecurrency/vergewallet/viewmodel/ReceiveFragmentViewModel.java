package vergecurrency.vergewallet.viewmodel;

import androidx.lifecycle.ViewModel;
import vergecurrency.vergewallet.wallet.WalletManager;

public class ReceiveFragmentViewModel extends ViewModel {

	private String receiveAddress;

	public ReceiveFragmentViewModel() {
		receiveAddress = WalletManager.getReceiveAddress();
	}

	public String getReceiveAddress() {
		return receiveAddress;
	}

	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}

	public void newReceiveAddress() {
	}
}
