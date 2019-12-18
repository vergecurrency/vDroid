package vergecurrency.vergewallet.service.model.network.layers

import android.os.AsyncTask
import cz.msebera.android.httpclient.client.methods.HttpGet
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder
import cz.msebera.android.httpclient.util.EntityUtils


class ClearnetGateway : AsyncTask<String, Void, String>() {


    override fun doInBackground(vararg url: String): String? {
        try {

            val httpClient = HttpClientBuilder.create().build()
            val response = httpClient.execute(HttpGet(url[0]))

            // StatusLine stat = response.getStatusLine();
            val status = response.statusLine.statusCode

            if (status == 200) {
                val entity = response.entity


                return EntityUtils.toString(entity)
            } else
                return ""

        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }


}
