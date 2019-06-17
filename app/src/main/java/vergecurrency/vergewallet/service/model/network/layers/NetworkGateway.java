package vergecurrency.vergewallet.service.model.network.layers;


import android.os.AsyncTask;

import java.util.concurrent.ExecutionException;

import static vergecurrency.vergewallet.wallet.VergeWalletApplication.PREFERENCES_MANAGER;

public class NetworkGateway extends AsyncTask<String, Integer, String> {

	@Override
	protected String doInBackground(String... strings) {
		String result;
		if (PREFERENCES_MANAGER.getUsingTor()) {
			//TODO : Redo tg class
			TorLayerGateway tg = TorLayerGateway.getInstance();
			result = tg.doInBackground(strings);
		} else {
			try {
				result = new ClearnetGateway().execute(strings).get();
			} catch (ExecutionException e) {
				e.printStackTrace();
				result = "";
			} catch (InterruptedException e) {
				e.printStackTrace();
				result = "";
			}
		}


		return result;
	}
}
