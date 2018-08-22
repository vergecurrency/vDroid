package vergecurrency.vergewallet;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.msopentech.thali.toronionproxy.OnionProxyManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import vergecurrency.vergewallet.UI.fragments.FragmentReceive;
import vergecurrency.vergewallet.UI.fragments.FragmentSend;
import vergecurrency.vergewallet.UI.fragments.FragmentSettings;
import vergecurrency.vergewallet.UI.fragments.FragmentTransactions;
import vergecurrency.vergewallet.UI.fragments.FragmentWallet;
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
import vergecurrency.vergewallet.Workers.ConnectionSocket;
import vergecurrency.vergewallet.Workers.SSLConnectionSocket;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //THIS SHIT HAS TO BE MOVED BUT WORKS FOR NOW.
        new TorTask().execute();


        mTextMessage = (TextView) findViewById(R.id.mTextMessage);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);

        showFragment(new FragmentWallet(), R.string.title_wallet,Color.WHITE,getResources().getColor(R.color.colorPrimary));

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

    //I have to move this shit but honestly dude it works so don't bother me.
    private class TorTask extends android.os.AsyncTask<String, Integer, String> {


        //Async task, so works while not bothering everybody. It's the android version of a thread.
        @Override
        protected String doInBackground(String... strings) {
            String fileStorageLocation = "torfiles";

            //Get the proxy manager
            OnionProxyManager onionProxyManager =
                    new com.msopentech.thali.android.toronionproxy.AndroidOnionProxyManager(getApplicationContext(), fileStorageLocation);
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
                HttpGet httpGet = new HttpGet("http://wikitjerrta4qgz4.onion/");
                HttpResponse httpResponse = httpClient.execute(httpGet, context);
                HttpEntity httpEntity = httpResponse.getEntity();
                InputStream httpResponseStream = httpEntity.getContent();

                //Reads the whole content because I had nothing better to do and followed a well documented example
                //TODO : Externalise all this in a function to treat different data models like the Cryptocompare API result
                BufferedReader httpResponseReader = new BufferedReader(
                        new InputStreamReader(httpResponseStream, "iso-8859-1"), 8);
                String line = null;
                while ((line = httpResponseReader.readLine()) != null) {
                    System.out.println(line);
                }
                httpResponseStream.close();
            }
            //TODO : Catch exception in a better way
            catch (Exception e) {
                e.printStackTrace();
            }
            return "done!";
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String result) {

        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_wallet:
                    showFragment(new FragmentWallet(), R.string.title_wallet,Color.WHITE,getResources().getColor(R.color.colorPrimary));

                    return true;
                case R.id.navigation_transactions:
                    showFragment(new FragmentTransactions(), R.string.title_transactions,getResources().getColor(R.color.colorPrimaryDark),Color.WHITE);

                    return true;
                case R.id.navigation_send:
                    showFragment(new FragmentSend(), R.string.title_send,getResources().getColor(R.color.colorPrimaryDark),Color.WHITE);

                    return true;
                case R.id.navigation_receive:
                    showFragment(new FragmentReceive(), R.string.title_receive,getResources().getColor(R.color.colorPrimaryDark),Color.WHITE);

                    return true;
                case R.id.navigation_settings:
                    showFragment(new FragmentSettings(), R.string.title_settings,getResources().getColor(R.color.colorPrimaryDark),Color.WHITE);
                    return true;
            }
            return false;
        }
    };

    private void showFragment(Fragment fragment, int title, int textColor, int textBgColor) {
        mTextMessage.setText(title);
        mTextMessage.setTextColor(textColor);
        mTextMessage.setBackgroundColor(textBgColor);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private TextView mTextMessage;
}
