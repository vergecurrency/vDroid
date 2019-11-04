package vergecurrency.vergewallet.service.model.network.layers

import android.content.Context
import android.os.AsyncTask

import com.msopentech.thali.toronionproxy.OnionProxyManager

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.URI
import java.net.UnknownHostException
import java.nio.charset.StandardCharsets

import cz.msebera.android.httpclient.HttpEntity
import cz.msebera.android.httpclient.HttpResponse
import cz.msebera.android.httpclient.client.HttpClient
import cz.msebera.android.httpclient.client.methods.HttpGet
import cz.msebera.android.httpclient.client.protocol.HttpClientContext
import cz.msebera.android.httpclient.config.Registry
import cz.msebera.android.httpclient.config.RegistryBuilder
import cz.msebera.android.httpclient.conn.DnsResolver
import cz.msebera.android.httpclient.conn.socket.ConnectionSocketFactory
import cz.msebera.android.httpclient.impl.client.HttpClients
import cz.msebera.android.httpclient.impl.conn.PoolingHttpClientConnectionManager
import cz.msebera.android.httpclient.ssl.SSLContexts
import vergecurrency.vergewallet.service.model.network.sockets.ConnectionSocket
import vergecurrency.vergewallet.service.model.network.sockets.SSLConnectionSocket


class TorLayerGateway private constructor() : AsyncTask<String, Int, String>() {
    private lateinit var context: Context
    private var onionProxyManager: OnionProxyManager? = null
    @Volatile
    var isConnected: Boolean = false
        private set

    //Http client : registers a socket according to a given protocol
    val newHttpClient: HttpClient
        get() {

            val reg = RegistryBuilder.create<ConnectionSocketFactory>()
                    .register("http", ConnectionSocket())
                    .register("https", SSLConnectionSocket(SSLContexts.createSystemDefault()))
                    .build()
            val cm = PoolingHttpClientConnectionManager(reg, FakeDnsResolver())
            return HttpClients.custom()
                    .setConnectionManager(cm)
                    .build()
        }

    //Async task, so works while not bothering everybody.
    public override fun doInBackground(vararg strings: String): String {
        val fileStorageLocation = "torfiles"

        //Get the proxy manager
        onionProxyManager = com.msopentech.thali.android.toronionproxy.AndroidOnionProxyManager(context, fileStorageLocation)
        val totalSecondsPerTorStartup = 4 * 10
        val totalTriesPerTorStartup = 2
        try {
            //starts tor by trying 240s x 5 times.
            val ok = onionProxyManager!!.startWithRepeat(totalSecondsPerTorStartup, totalTriesPerTorStartup)
            if (!ok)
                println("Couldn't start tor")
            else {
                isConnected = true
            }
            while (!onionProxyManager!!.isRunning) {
                //Puts the thread to sleep while tor isn't running
                Thread.sleep(90)
            }
            println("Tor initialized on port " + onionProxyManager!!.iPv4LocalHostSocksPort)

        } catch (e: Exception) {
            e.printStackTrace()
            isConnected = false
        }
        //TODO : Catch exception in a better way
        return "done!"
    }

    fun retrieveDataFromService(uri: String): String? {
        try {

            val result = StringBuilder()

            //Creates the http client according to the previous method
            val port = onionProxyManager!!.iPv4LocalHostSocksPort
            //creates the local socket and context
            val socksaddr = InetSocketAddress("127.0.0.1", port)
            val context = HttpClientContext.create()
            context.setAttribute("socks.address", socksaddr)

            val httpClient = newHttpClient

            val httpGet = HttpGet(URI(uri))
            val httpResponse = httpClient.execute(httpGet, context)
            val httpEntity = httpResponse.entity
            val httpResponseStream = httpEntity.content

            //Reads the whole content because I had nothing better to do and followed a well documented example
            val httpResponseReader = BufferedReader(
                    InputStreamReader(httpResponseStream, StandardCharsets.ISO_8859_1), 8)

            var line: String? = null

            while ({line = httpResponseReader.readLine(); line}() != null) {
                result.append(line)
            }
            httpResponseStream.close()

            return result.toString()
        } catch (ex: Exception) {
            //TODO : Catch exception properly
            ex.printStackTrace()
            return null
        }

    }

    override fun onPreExecute() {

    }


    //Variables come here

    override fun onPostExecute(result: String) {

    }

    //Talks for itself
    internal class FakeDnsResolver : DnsResolver {
        @Throws(UnknownHostException::class)
        override fun resolve(host: String): Array<InetAddress> {
            return arrayOf(InetAddress.getByAddress(byteArrayOf(1, 1, 1, 1)))
        }
    }

    companion object {


        //Let's make this piece of sh... class a singleton.
        val instance = TorLayerGateway()
    }

    /*	private boolean launchTor() {

		//TODO : Once controller has been implemented delete this, it's just to not block http client called from main threads.
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		ApifyService adr = new ApifyService();


		TorLayerGateway tlg = TorLayerGateway.getInstance();
		tlg.setContext(getApplicationContext());
		//TODO : Check whether there's internet connection first.
		//tlg.execute();

		//Start timeout counter
		int timeoutCounter = -1;

		while (timeoutCounter < 10) {
			//increase timeout after one cycle
			//timeoutCounter++;
			if (true) {
				// if (tlg.isConnected()) {
	//Get the current public IP, just for fun honestly.
	String IP = adr.readIP(tlg.retrieveDataFromService("https://api.ipify.org?format=json"));

				Toast.makeText(getApplicationContext(), String.format("Tor connected. IP : %s",IP), Toast.LENGTH_LONG).show();
	//return tlg.isConnected();
				return true;
			} else {
		//Implement timeout
		try {
		Thread.sleep(3000);
		} catch (InterruptedException e) {
		e.printStackTrace();
		}
		}

		}
		return false;
		}

		*/


}




