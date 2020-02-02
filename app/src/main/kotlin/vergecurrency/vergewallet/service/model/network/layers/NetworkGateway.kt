package vergecurrency.vergewallet.service.model.network.layers

import vergecurrency.vergewallet.service.model.EncryptedPreferencesManager

open class NetworkGateway {


    fun doRequest(request: String): String {
        var result = ""
        if (EncryptedPreferencesManager.usingTor) {

            result = TorLayerGateway().execute(request).get()
        } else {
            result = ClearnetGateway().execute(request).get()
        }

        return result
    }
}