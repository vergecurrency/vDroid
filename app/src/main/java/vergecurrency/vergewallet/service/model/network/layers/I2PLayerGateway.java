package vergecurrency.vergewallet.service.model.network.layers;

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
import vergecurrency.vergewallet.service.model.network.sockets.ConnectionSocket;
import vergecurrency.vergewallet.service.model.network.sockets.SSLConnectionSocket;
import net.i2p.router.Router;
import java.util.Properties;

public class I2PLayerGateway extends NetworkGateway {
    private Router r;
    
    
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
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(reg,new I2PLayerGateway.FakeDnsResolver());
        return HttpClients.custom()
                .setConnectionManager(cm)
                .build();
    }

    @Override
    protected String doInBackground(String... strings) {
        
        Properties props = new Properties();
        props.setProperty("i2p.dir.base", "baseDir");
        props.setProperty("i2p.dir.config", "configDir");
        props.setProperty("i2np.inboundKBytesPerSecond", "50");
        props.setProperty("i2np.outboundKBytesPerSecond", "50");
        props.setProperty("router.sharePercentage", "80");
        r = new Router(props);
        
        try {
            r.setKillVMOnEnd(false);
            r.runRouter();
            return "done";
        } catch( Exception e) {
            e.printStackTrace();

            return "oh lol shit";
        }
    }
    
    //Because I'm not savage LOL
    private void killGently() {
        if(r != null)
        r.shutdownGracefully();
    }
    
}
