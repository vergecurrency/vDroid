package vergecurrency.vergewallet.service.model.network.layers

import android.content.Context
import com.msopentech.thali.android.toronionproxy.AndroidOnionProxyManager
import com.msopentech.thali.toronionproxy.OnionProxyManager
import cz.msebera.android.httpclient.conn.DnsResolver
import java.lang.IllegalStateException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Proxy
import java.net.UnknownHostException

//TODO : add observer
class TorManager private constructor(context: Context) {

    var onionProxyManager: OnionProxyManager? = null
    val fileStorageLocation = "torfiles"
    var proxy: Proxy? = null
    var currentPort = 0

     enum class STATES {
        IDLE,
        CONNECTED,
        DISCONNECTED,
        CONNECTING,
        ERROR
    }

     var state : STATES = STATES.IDLE

    init {
        onionProxyManager = AndroidOnionProxyManager(context, fileStorageLocation)
    }

    companion object : SingletonHolder<TorManager, Context>(::TorManager)

     fun startTor(): Proxy? {


        if (state == STATES.CONNECTED) {
            return null
        }
        state = STATES.CONNECTING

        val totalSecondsPerTorStartup = 4 * 60
        val totalTriesPerTorStartup = 5

        val ok = try {
            onionProxyManager!!.startWithRepeat(totalSecondsPerTorStartup, totalTriesPerTorStartup)
        } catch (e: InterruptedException) {
            false
        }

        if (!ok) {
            state = STATES.ERROR
            println("Couldn't start tor")
            return null
        }

        while (!onionProxyManager!!.isRunning) {
            Thread.sleep(90)
        }

        proxy = Proxy(Proxy.Type.SOCKS, InetSocketAddress("127.0.0.1", onionProxyManager!!.iPv4LocalHostSocksPort))
        currentPort =  onionProxyManager!!.iPv4LocalHostSocksPort
        state = STATES.CONNECTED

        println("Tor initialized on port " + onionProxyManager!!.iPv4LocalHostSocksPort)
        return proxy
    }

    fun isConnected() : Boolean {
        return state == STATES.CONNECTED
    }


    //Talks for itself
    internal class FakeDnsResolver : DnsResolver {
        @Throws(UnknownHostException::class)
        override fun resolve(host: String): Array<InetAddress> {
            return arrayOf(InetAddress.getByAddress(byteArrayOf(1, 1, 1, 1)))
        }
    }

}


open class SingletonHolder<out T: Any, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator
    @Volatile private var instance: T? = null

    fun getInstance(arg: A?): T {
        val checkInstance = instance
        if (checkInstance != null) {
            return checkInstance
        }
        if(arg == null) {
            throw IllegalStateException();
        }
        return synchronized(this) {
            val checkInstanceAgain = instance
            if (checkInstanceAgain != null) {
                checkInstanceAgain
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}



