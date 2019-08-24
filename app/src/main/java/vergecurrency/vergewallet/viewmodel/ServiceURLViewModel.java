package vergecurrency.vergewallet.viewmodel;

import androidx.lifecycle.ViewModel;

import vergecurrency.vergewallet.Constants;
import vergecurrency.vergewallet.service.model.PreferencesManager;

public class ServiceURLViewModel extends ViewModel {

	public String getCurrentServiceURL() {
		return PreferencesManager.getWalletServiceUrl();
	}

	public void setNewServiceURL(String serviceURL) {
		PreferencesManager.setWalletServiceUrl(serviceURL);
	}

	public void setDefaultServiceURL(){
		setNewServiceURL(Constants.VWS_ENDPOINT);
	}
}
