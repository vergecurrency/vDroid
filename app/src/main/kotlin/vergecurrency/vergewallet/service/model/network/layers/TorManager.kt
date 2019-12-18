package vergecurrency.vergewallet.service.model.network.layers

import android.content.Context
import com.msopentech.thali.toronionproxy.OnionProxyManager
import cz.msebera.android.httpclient.client.HttpClient
import cz.msebera.android.httpclient.config.RegistryBuilder
import cz.msebera.android.httpclient.conn.DnsResolver
import cz.msebera.android.httpclient.conn.socket.ConnectionSocketFactory
import cz.msebera.android.httpclient.impl.client.HttpClients
import cz.msebera.android.httpclient.impl.conn.PoolingHttpClientConnectionManager
import cz.msebera.android.httpclient.ssl.SSLContexts
import vergecurrency.vergewallet.service.model.network.sockets.ConnectionSocket
import vergecurrency.vergewallet.service.model.network.sockets.SSLConnectionSocket
import java.net.InetAddress
import java.net.UnknownHostException


object TorManager {

    var onionProxyManager: OnionProxyManager? = null

    @Volatile
    var isConnected: Boolean = false

    @Volatile
    var initError: Boolean = false

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


    fun startTor(context: Context) {

        if (isConnected) {
            return
        }

        val fileStorageLocation = "torfiles"

        //Get the proxy manager
        onionProxyManager = com.msopentech.thali.android.toronionproxy.AndroidOnionProxyManager(context, fileStorageLocation)
        val totalSecondsPerTorStartup = 4 * 10
        val totalTriesPerTorStartup = 2

        val ok = try {
            onionProxyManager!!.startWithRepeat(totalSecondsPerTorStartup, totalTriesPerTorStartup)
        } catch (e: InterruptedException) {
            false
        }
        if (!ok) {
            initError = true
            println("Couldn't start tor")
        } else {
            isConnected = true
        }
        while (!onionProxyManager!!.isRunning) {
            //Puts the thread to sleep while tor isn't running
            Thread.sleep(90)
        }
        println("Tor initialized on port " + onionProxyManager!!.iPv4LocalHostSocksPort)

    }


    //Talks for itself
    internal class FakeDnsResolver : DnsResolver {
        @Throws(UnknownHostException::class)
        override fun resolve(host: String): Array<InetAddress> {
            return arrayOf(InetAddress.getByAddress(byteArrayOf(1, 1, 1, 1)))
        }
    }


}




