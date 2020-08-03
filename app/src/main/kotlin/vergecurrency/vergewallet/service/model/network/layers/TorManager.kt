package vergecurrency.vergewallet.service.model.network.layers

import android.content.Context
import android.os.AsyncTask
import com.msopentech.thali.android.toronionproxy.AndroidOnionProxyManager
import com.msopentech.thali.toronionproxy.OnionProxyManager
import cz.msebera.android.httpclient.client.HttpClient
import cz.msebera.android.httpclient.config.RegistryBuilder
import cz.msebera.android.httpclient.conn.DnsResolver
import cz.msebera.android.httpclient.conn.socket.ConnectionSocketFactory
import cz.msebera.android.httpclient.impl.client.HttpClients
import cz.msebera.android.httpclient.impl.conn.PoolingHttpClientConnectionManager
import cz.msebera.android.httpclient.ssl.SSLContexts
import io.reactivex.rxjava3.subjects.PublishSubject
import vergecurrency.vergewallet.service.model.network.sockets.ConnectionSocket
import vergecurrency.vergewallet.service.model.network.sockets.SSLConnectionSocket
import java.lang.IllegalStateException
import java.net.*

class TorManager private constructor(context: Context)  :  AsyncTask<String, Void, String>() {

    var onionProxyManager: OnionProxyManager? = null
    private val fileStorageLocation = "torfiles"
    private var proxy: Proxy? = null
    private var currentPort = 0


    enum class STATES {
        IDLE,
        CONNECTED,
        DISCONNECTED,
        CONNECTING,
        ERROR
    }

    var torStatus: PublishSubject<STATES> = PublishSubject.create();

    var state: STATES = STATES.IDLE

    init {
        torStatus.onNext(STATES.DISCONNECTED)
        onionProxyManager = AndroidOnionProxyManager(context, fileStorageLocation)
    }

    companion object : SingletonHolder<TorManager, Context>(::TorManager)

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


    fun startTor() : String {


        if (state == STATES.CONNECTED) {
            return "connected"
        }
        state = STATES.CONNECTING

        val totalSecondsPerTorStartup = 4 * 60
        val totalTriesPerTorStartup = 5

        val ok = try {
            onionProxyManager!!.startWithRepeat(totalSecondsPerTorStartup, totalTriesPerTorStartup)
        } catch (e: Exception) {
            return "ugh"
        }

        if (!ok) {
            state = STATES.ERROR
            println("Couldn't start tor")
            if (torStatus.hasObservers()) {
                torStatus.onNext(STATES.ERROR)
            }
        }


        proxy = Proxy(Proxy.Type.SOCKS, InetSocketAddress("127.0.0.1", onionProxyManager!!.iPv4LocalHostSocksPort))
        currentPort = onionProxyManager!!.iPv4LocalHostSocksPort

        if (torStatus.hasObservers()) {
            torStatus.onNext(STATES.CONNECTED)
        }

        state = STATES.CONNECTED

        println("Tor initialized on port " + onionProxyManager!!.iPv4LocalHostSocksPort)
        return "ok"
    }

    fun stopTor(): Boolean {
        if (torStatus.hasObservers()) {
            torStatus.onNext(STATES.DISCONNECTED)
        }


        try {
            this.state = STATES.DISCONNECTED
            if (torStatus.hasObservers()) {
                torStatus.onNext(STATES.DISCONNECTED)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            this.state = STATES.DISCONNECTED
            if (torStatus.hasObservers()) {
                torStatus.onNext(STATES.DISCONNECTED)
            }

            return false
        }
        return true

    }

    fun isPortOpen(ip: String, port: Int, timeout: Int): Boolean {
        try {
            val socket = Socket()
            socket.connect(InetSocketAddress(ip, port), timeout)
            socket.close()
            return true
        } catch (ce: ConnectException) {
            ce.printStackTrace()
            return false
        } catch (ex: Exception) {
            ex.printStackTrace()
            return false
        }
    }

    override fun doInBackground(vararg p0: String?): String {
        return startTor()
    }

}


open class SingletonHolder<out T : Any, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator

    @Volatile
    private var instance: T? = null

    fun getInstance(arg: A?): T {
        val checkInstance = instance
        if (checkInstance != null) {
            return checkInstance
        }
        return synchronized(this) {
            val checkInstanceAgain = instance
            if (checkInstanceAgain != null) {
                checkInstanceAgain
            } else {
                if (arg == null) {
                    throw IllegalStateException();
                }
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}

internal class FakeDnsResolver : DnsResolver {
    @Throws(UnknownHostException::class)
    override fun resolve(host: String): Array<InetAddress> {
        return arrayOf(InetAddress.getByAddress(byteArrayOf(1, 1, 1, 1)))
    }
}


