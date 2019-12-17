package vergecurrency.vergewallet.service.model.network.layers


import vergecurrency.vergewallet.service.model.PreferencesManager


open class NetworkGateway {


    fun doRequest(request: String): String {
        var result: String
        if (PreferencesManager.usingTor) {
            val tg = TorLayerGateway.instance
            result = tg.doInBackground(request)
        } else {
            result = ClearnetGateway().execute(request).get()
        }

        return result
    }
}