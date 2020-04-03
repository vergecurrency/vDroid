package vergecurrency.vergewallet.service.model.network.layers

import android.os.AsyncTask
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse
import cz.msebera.android.httpclient.client.methods.HttpDelete
import cz.msebera.android.httpclient.client.methods.HttpGet
import cz.msebera.android.httpclient.client.methods.HttpPost
import cz.msebera.android.httpclient.entity.StringEntity
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder
import cz.msebera.android.httpclient.util.EntityUtils


class ClearnetGateway : AsyncTask<String, Void, String>() {



    override fun doInBackground(vararg params : String ): String? {
        var url = params[0]
        var arguments = params[1]
        var type = params[2]

        try {

            val httpClient = HttpClientBuilder.create().build()
            var response : CloseableHttpResponse

            if (type=="GET") {
                response = httpClient.execute(HttpGet(url))

            }else if (type=="POST"){
                var postRequest = HttpPost(url)
                postRequest.entity = StringEntity(arguments)
                response = httpClient.execute(postRequest)

            } else if (type == "DELETE"){
                response = httpClient.execute(HttpDelete(url))

            } else {
                response = httpClient.execute(HttpGet(url))
            }


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
