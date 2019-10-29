package vergecurrency.vergewallet.service.model.wallet;

import androidx.annotation.Nullable;

public class WalletAddressesOptions {
	@Nullable
	private int limit;
	private boolean reverse = false;

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public boolean isReverse() {
		return reverse;
	}

	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}
}
