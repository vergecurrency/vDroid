package vergecurrency.vergewallet.service.model.network.layers;


import android.os.AsyncTask;

import java.util.concurrent.ExecutionException;

import vergecurrency.vergewallet.service.model.PreferencesManager;


public class NetworkGateway extends AsyncTask<String, Integer, String> {

	@Override
	protected String doInBackground(String... strings) {
		String result;
		if (PreferencesManager.getUsingTor()) {
			//TODO : Redo tg class
			TorLayerGateway tg = TorLayerGateway.getInstance();
			result = tg.doInBackground(strings);
		} else {
			/*try {
				//TODO = fix this.
				result = new ClearnetGateway().execute(strings).get();
			} catch (ExecutionException e) {
				e.printStackTrace();
				result = "";
			} catch (InterruptedException e) {
				e.printStackTrace();
				result = "";
			}*/
			result = "";
		}


		return result;
	}
}
