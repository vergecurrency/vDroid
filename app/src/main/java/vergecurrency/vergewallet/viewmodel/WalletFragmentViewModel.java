package vergecurrency.vergewallet.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import vergecurrency.vergewallet.service.model.Currency;
import vergecurrency.vergewallet.service.model.PreferencesManager;

public class WalletFragmentViewModel extends ViewModel {


	public String getCurrencyCode() {
		return Currency.getCurrencyFromJson(PreferencesManager.getInstance().getPreferredCurrency()).getCurrency();
	}
}
