package vergecurrency.vergewallet.service.model.network.layers

import android.os.AsyncTask
import cz.msebera.android.httpclient.client.methods.HttpRequestBase

abstract class Gateway(requestBase: HttpRequestBase?) : AsyncTask<String, Void, String>() {
    protected val requestBase = requestBase
    override fun doInBackground(vararg params: String?): String {
     return this.background(*params);
    }

    abstract fun background(vararg params: String?): String

}