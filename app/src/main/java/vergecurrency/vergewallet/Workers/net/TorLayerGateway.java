package vergecurrency.vergewallet.Workers;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.msopentech.thali.toronionproxy.OnionProxyManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.UnknownHostException;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.protocol.HttpClientContext;
import cz.msebera.android.httpclient.config.Registry;
import cz.msebera.android.httpclient.config.RegistryBuilder;
import cz.msebera.android.httpclient.conn.DnsResolver;
import cz.msebera.android.httpclient.conn.socket.ConnectionSocketFactory;
import cz.msebera.android.httpclient.impl.client.HttpClients;
import cz.msebera.android.httpclient.impl.conn.PoolingHttpClientConnectionManager;
import cz.msebera.android.httpclient.ssl.SSLContexts;



//I have to move this shit but honestly dude it works so don't bother me.
public class TorLayerGateway extends android.os.AsyncTask<String, Integer, String>{

    private Context context;
    private OnionProxyManager onionProxyManager;


    public TorLayerGateway(Context context) {
        this.context = context;
    }

    //Talks for itself
    static class FakeDnsResolver implements DnsResolver {
        @Override
        public InetAddress[] resolve(String host) throws UnknownHostException {
            return new InetAddress[] { InetAddress.getByAddress(new byte[] { 1, 1, 1, 1 }) };
        }
    }

    //Http client : registers according to protocol a socket and builds itself
    public HttpClient getNewHttpClient() {

        Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", new ConnectionSocket())
                .register("https", new SSLConnectionSocket(SSLContexts.createSystemDefault()))
                .build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(reg,new FakeDnsResolver());
        return HttpClients.custom()
                .setConnectionManager(cm)
                .build();
    }



    //Async task, so works while not bothering everybody. It's the android version of a thread.
    @Override
    protected String doInBackground(String... strings) {
        String fileStorageLocation = "torfiles";

        //Get the proxy manager
         onionProxyManager = new com.msopentech.thali.android.toronionproxy.AndroidOnionProxyManager(context, fileStorageLocation);
        int totalSecondsPerTorStartup = 4 * 60;
        int totalTriesPerTorStartup = 5;
        try {
            //starts tor by trying 240s x 5 times.
            boolean ok = onionProxyManager.startWithRepeat(totalSecondsPerTorStartup, totalTriesPerTorStartup);
            if (!ok)
                System.out.println("Couldn't start tor");

            while (!onionProxyManager.isRunning()) {
                //Puts the thread to sleep while tor isn't running
                Thread.sleep(90);
            }
            System.out.println("Tor initialized on port " + onionProxyManager.getIPv4LocalHostSocksPort());

            //Creates the http client according to the previous method
            HttpClient httpClient = getNewHttpClient();
            int port = onionProxyManager.getIPv4LocalHostSocksPort();
            //creates the local socket and context
            InetSocketAddress socksaddr = new InetSocketAddress("127.0.0.1", port);
            HttpClientContext context = HttpClientContext.create();
            context.setAttribute("socks.address", socksaddr);


            //Try to access http://wikitjerrta4qgz4.onion/ which is the hidden wiki
            //TODO : Replace this with an argument and externalise, because tor connection is not going to be instantiated everytime.
            //HttpGet httpGet = new HttpGet("http://wikitjerrta4qgz4.onion/");
            HttpGet httpGet = new HttpGet("https://api.ipify.org?format=json");
            HttpResponse httpResponse = httpClient.execute(httpGet, context);
            HttpEntity httpEntity = httpResponse.getEntity();
            InputStream httpResponseStream = httpEntity.getContent();

            //Reads the whole content because I had nothing better to do and followed a well documented example
            //TODO : Externalise all this in a function to treat different data models like the Cryptocompare API result
            BufferedReader httpResponseReader = new BufferedReader(
                    new InputStreamReader(httpResponseStream, "iso-8859-1"), 8);
            String line = null;
            while ((line = httpResponseReader.readLine()) != null) {
                System.out.println("VERGE WALLET IP : " + line);
            }
            httpResponseStream.close();
        }
        //TODO : Catch exception in a better way
        catch (Exception e) {
            e.printStackTrace();
        }
        return "done!";
    }


    public void retrieveDataFromURI(String uri) {
        try {
            //Creates the http client according to the previous method
            HttpClient httpClient = getNewHttpClient();
            int port = onionProxyManager.getIPv4LocalHostSocksPort();
            //creates the local socket and context
            InetSocketAddress socksaddr = new InetSocketAddress("127.0.0.1", port);
            HttpClientContext context = HttpClientContext.create();
            context.setAttribute("socks.address", socksaddr);

            //URL Current IP : https://api.ipify.org?format=json
            //

            HttpGet httpGet = new HttpGet(new URI(uri));
            HttpResponse httpResponse = httpClient.execute(httpGet, context);
            HttpEntity httpEntity = httpResponse.getEntity();
            InputStream httpResponseStream = httpEntity.getContent();

            //Reads the whole content because I had nothing better to do and followed a well documented example
            //TODO : Externalise all this in a function to treat different data models like the Cryptocompare API result
            BufferedReader httpResponseReader = new BufferedReader(
                    new InputStreamReader(httpResponseStream, "iso-8859-1"), 8);
            String line = null;
            while ((line = httpResponseReader.readLine()) != null) {
                System.out.println("VERGE WALLET IP : " + line);
            }
            httpResponseStream.close();
        } catch (Exception ex) {
            //TODO : Catch exception properly
        }
    }



    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(String result) {

    }


}




