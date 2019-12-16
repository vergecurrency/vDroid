package vergecurrency.vergewallet.service.model.network.layers


import android.content.Context
import android.os.AsyncTask
import vergecurrency.vergewallet.helpers.utils.NetworkUtils
import vergecurrency.vergewallet.service.model.PreferencesManager


open class NetworkGateway(ct: Context)  {

    var context = ct

     fun doRequest(request: String): String {
        var result: String
        if (NetworkUtils.checkNetworkState(context)) {


            if (PreferencesManager.usingTor) {
                //TODO : Redo tg class
                val tg = TorLayerGateway.instance
                result = tg.doInBackground(request)
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

        } else {
            result = ""

        }
        return result
    }
}