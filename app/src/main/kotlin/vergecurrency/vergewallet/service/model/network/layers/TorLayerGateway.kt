package vergecurrency.vergewallet.service.model.network.layers

import android.os.AsyncTask
import cz.msebera.android.httpclient.client.methods.HttpGet
import cz.msebera.android.httpclient.client.protocol.HttpClientContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.InetSocketAddress
import java.net.URI
import java.nio.charset.StandardCharsets


class TorLayerGateway : AsyncTask<String, Int, String>() {

    public override fun doInBackground(vararg params: String): String {

        var url = params[0]
        var arguments = params[1]

        if (TorManager.initError) {
            //return ClearnetGateway().execute(strings[0]).get()
            return ""
        }

        if (TorManager.isConnected) {
            return doRequest(url, arguments)!!
        } else return ""
    }

    fun doRequest(uri: String, arguments : String): String? {
        try {

            val result = StringBuilder()

            //Creates the http client according to the previous method
            val port = TorManager.onionProxyManager!!.iPv4LocalHostSocksPort
            //creates the local socket and context
            val socksaddr = InetSocketAddress("127.0.0.1", port)
            val context = HttpClientContext.create()
            context.setAttribute("socks.address", socksaddr)

            val httpClient = TorManager.newHttpClient

            val httpGet = HttpGet(URI(uri))
            val httpResponse = httpClient.execute(httpGet, context)
            val httpEntity = httpResponse.entity
            val httpResponseStream = httpEntity.content

            //Reads the whole content because I had nothing better to do and followed a well documented example
            val httpResponseReader = BufferedReader(
                    InputStreamReader(httpResponseStream, StandardCharsets.ISO_8859_1), 8)

            var line: String? = null

            while ({ line = httpResponseReader.readLine(); line }() != null) {
                result.append(line)
            }
            httpResponseStream.close()

            return result.toString()
        } catch (ex: Exception) {
            //TODO : Catch exception properly
            ex.printStackTrace()
            return ""
        }
    }

    override fun onPreExecute() {

    }

    override fun onPostExecute(result: String) {

    }


}




