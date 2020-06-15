package vergecurrency.vergewallet.service.model.network.layers

import cz.msebera.android.httpclient.client.methods.HttpRequestBase
import vergecurrency.vergewallet.service.model.EncryptedPreferencesManager

open class NetworkGateway {


    fun doRequest(requestBase: HttpRequestBase): String {
        var result = ""
        //I want to get around tor for instance
        if (EncryptedPreferencesManager.usingTor && 1 ==0 ) {
            result = TorLayerGateway(requestBase).execute().get()
        } else {
            result = ClearnetGateway(requestBase).execute().get()
        }

        return result
    }
}