package vergecurrency.vergewallet.service.model.network.layers

import cz.msebera.android.httpclient.client.HttpClient
import cz.msebera.android.httpclient.config.RegistryBuilder
import cz.msebera.android.httpclient.conn.DnsResolver
import cz.msebera.android.httpclient.conn.socket.ConnectionSocketFactory
import cz.msebera.android.httpclient.impl.client.HttpClients
import cz.msebera.android.httpclient.impl.conn.PoolingHttpClientConnectionManager
import cz.msebera.android.httpclient.ssl.SSLContexts
//import net.i2p.router.Router
import vergecurrency.vergewallet.service.model.network.sockets.ConnectionSocket
import vergecurrency.vergewallet.service.model.network.sockets.SSLConnectionSocket
import java.net.InetAddress
import java.net.UnknownHostException
import java.util.*

class I2PLayerGateway {
    //private var r: Router? = null

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


    //Talks for itself
    internal class FakeDnsResolver : DnsResolver {
        @Throws(UnknownHostException::class)
        override fun resolve(host: String): Array<InetAddress> {
            return arrayOf(InetAddress.getByAddress(byteArrayOf(1, 1, 1, 1)))
        }
    }

    fun doInBackground(vararg strings: String): String {

        val props = Properties()
        props.setProperty("i2p.dir.base", "baseDir")
        props.setProperty("i2p.dir.config", "configDir")
        props.setProperty("i2np.inboundKBytesPerSecond", "50")
        props.setProperty("i2np.outboundKBytesPerSecond", "50")
        props.setProperty("router.sharePercentage", "80")
        //r = Router(props)

        try {
            //r!!.killVMOnEnd = false
            //r!!.runRouter()
            return "done"
        } catch (e: Exception) {
            e.printStackTrace()

            return "oh lol shit"
        }

    }

    //Because I'm not savage LOL
    private fun killGently() {
       // if (r != null)
       //     r!!.shutdownGracefully()
    }

}
