package vergecurrency.vergewallet.service.model.network.layers

import cz.msebera.android.httpclient.client.methods.HttpRequestBase
import cz.msebera.android.httpclient.client.protocol.HttpClientContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.InetSocketAddress
import java.nio.charset.StandardCharsets


class TorLayerGateway(requestBase: HttpRequestBase) : Gateway(requestBase) {

    //TODO
    override fun onPreExecute() {
        if (TorManager.initError) {
            //return ClearnetGateway().execute(strings[0]).get()
             throw RuntimeException("Tor Manager Error")
        }

        if (!TorManager.isConnected) {
            throw RuntimeException("Tor no connection")
        }
    }

    override fun background(vararg params: String?): String {
        try {

            val result = StringBuilder()

            //Creates the http client according to the previous method
            val port = TorManager.onionProxyManager!!.iPv4LocalHostSocksPort
            //creates the local socket and context
            val socksaddr = InetSocketAddress("127.0.0.1", port)
            val context = HttpClientContext.create()
            context.setAttribute("socks.address", socksaddr)

            val httpClient = TorManager.newHttpClient

            val httpResponse = httpClient.execute(super.requestBase, context)
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
        }    }

    override fun onPostExecute(result: String) {

    }


}




