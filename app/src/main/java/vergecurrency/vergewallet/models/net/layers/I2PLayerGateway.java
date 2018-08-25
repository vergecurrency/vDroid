package vergecurrency.vergewallet.models.net.layers;

import android.os.AsyncTask;

import java.net.InetAddress;
import java.net.UnknownHostException;

import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.config.Registry;
import cz.msebera.android.httpclient.config.RegistryBuilder;
import cz.msebera.android.httpclient.conn.DnsResolver;
import cz.msebera.android.httpclient.conn.socket.ConnectionSocketFactory;
import cz.msebera.android.httpclient.impl.client.HttpClients;
import cz.msebera.android.httpclient.impl.conn.PoolingHttpClientConnectionManager;
import cz.msebera.android.httpclient.ssl.SSLContexts;
import vergecurrency.vergewallet.models.net.sockets.ConnectionSocket;
import vergecurrency.vergewallet.models.net.sockets.SSLConnectionSocket;

public class I2PLayerGateway extends AsyncTask<String, Integer, String> {


    //Talks for itself
    static class FakeDnsResolver implements DnsResolver {
        @Override
        public InetAddress[] resolve(String host) throws UnknownHostException {
            return new InetAddress[] { InetAddress.getByAddress(new byte[] { 1, 1, 1, 1 }) };
        }
    }

    //Http client : registers a socket according to a given protocol
    public HttpClient getNewHttpClient() {

        Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", new ConnectionSocket())
                .register("https", new SSLConnectionSocket(SSLContexts.createSystemDefault()))
                .build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(reg,new TorLayerGateway.FakeDnsResolver());
        return HttpClients.custom()
                .setConnectionManager(cm)
                .build();
    }

    @Override
    protected String doInBackground(String... strings) {
        return null;
    }
}
