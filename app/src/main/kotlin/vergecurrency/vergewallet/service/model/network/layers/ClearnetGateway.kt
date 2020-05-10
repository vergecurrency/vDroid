package vergecurrency.vergewallet.service.model.network.layers

import android.os.AsyncTask
import cz.msebera.android.httpclient.client.methods.*
import cz.msebera.android.httpclient.entity.StringEntity
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder
import cz.msebera.android.httpclient.util.EntityUtils


class ClearnetGateway(requestBase: HttpRequestBase) : Gateway(requestBase) {
    override fun background(vararg params: String?): String {
        try {
            val httpClient = HttpClientBuilder.create().build()
            val response =  httpClient.execute(super.requestBase);
            val status = response.statusLine.statusCode

            if (status == 200) {
                val entity = response.entity


                return EntityUtils.toString(entity)
            } else
                return ""

        } catch (e: Exception) {
            //TODO
            e.printStackTrace()
            return "";
        }
    }


}
