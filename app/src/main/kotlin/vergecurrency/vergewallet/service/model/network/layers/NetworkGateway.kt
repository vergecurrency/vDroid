package vergecurrency.vergewallet.service.model.network.layers


import android.os.AsyncTask

import java.util.concurrent.ExecutionException

import vergecurrency.vergewallet.service.model.PreferencesManager


open class NetworkGateway : AsyncTask<String, Int, String>() {

    override fun doInBackground(vararg strings: String): String {
        val result: String
        if (PreferencesManager.usingTor) {
            //TODO : Redo tg class
            val tg = TorLayerGateway.instance
            result = tg.doInBackground(*strings)
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
            result = ""
        }


        return result
    }
}
