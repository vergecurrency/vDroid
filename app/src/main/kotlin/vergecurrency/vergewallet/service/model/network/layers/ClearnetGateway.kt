package vergecurrency.vergewallet.service.model.network.layers

import android.os.AsyncTask

import cz.msebera.android.httpclient.HttpEntity
import cz.msebera.android.httpclient.HttpResponse
import cz.msebera.android.httpclient.client.HttpClient
import cz.msebera.android.httpclient.client.methods.HttpGet
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder
import cz.msebera.android.httpclient.util.EntityUtils


class ClearnetGateway : AsyncTask<String, Void, String>() {


    override fun doInBackground(vararg url: String): String? {
        try {


            //TODO : CHECK INTERNET CONNECTION YOU IDIOT
            val httppost = HttpGet(url[0])
            val httpClient = HttpClientBuilder.create().build()
            val response = httpClient.execute(httppost)

            // StatusLine stat = response.getStatusLine();
            val status = response.statusLine.statusCode

            if (status == 200) {
                val entity = response.entity


                return EntityUtils.toString(entity)
            } else
                return "error"

        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }
}
