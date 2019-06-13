package vergecurrency.vergewallet.service.model.network.layers;

import android.content.Context;
import android.os.AsyncTask;

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
import vergecurrency.vergewallet.service.model.network.sockets.ConnectionSocket;
import vergecurrency.vergewallet.service.model.network.sockets.SSLConnectionSocket;


public class TorLayerGateway extends AsyncTask<String, Integer, String> {


	//Let's make this piece of sh... class a singleton.
	private static final TorLayerGateway instance = new TorLayerGateway();
	private Context context;
	private OnionProxyManager onionProxyManager;
	private volatile boolean isConnected;


	private TorLayerGateway() {

	}

	public static TorLayerGateway getInstance() {
		return instance;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	//Http client : registers a socket according to a given protocol
	public HttpClient getNewHttpClient() {

		Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", new ConnectionSocket())
				.register("https", new SSLConnectionSocket(SSLContexts.createSystemDefault()))
				.build();
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(reg, new FakeDnsResolver());
		return HttpClients.custom()
				.setConnectionManager(cm)
				.build();
	}

	//Async task, so works while not bothering everybody.
	@Override
	protected String doInBackground(String... strings) {
		String fileStorageLocation = "torfiles";

		//Get the proxy manager
		onionProxyManager = new com.msopentech.thali.android.toronionproxy.AndroidOnionProxyManager(context, fileStorageLocation);
		int totalSecondsPerTorStartup = 4 * 10;
		int totalTriesPerTorStartup = 2;
		try {
			//starts tor by trying 240s x 5 times.
			boolean ok = onionProxyManager.startWithRepeat(totalSecondsPerTorStartup, totalTriesPerTorStartup);
			if (!ok)
				System.out.println("Couldn't start tor");
			else {
				isConnected = true;
			}
			while (!onionProxyManager.isRunning()) {
				//Puts the thread to sleep while tor isn't running
				Thread.sleep(90);
			}
			System.out.println("Tor initialized on port " + onionProxyManager.getIPv4LocalHostSocksPort());

		}
		//TODO : Catch exception in a better way
		catch (Exception e) {
			e.printStackTrace();
			isConnected = false;
		}
		return "done!";
	}

	public String retrieveDataFromService(String uri) {
		try {

			String result = "";

			//Creates the http client according to the previous method
			int port = onionProxyManager.getIPv4LocalHostSocksPort();
			//creates the local socket and context
			InetSocketAddress socksaddr = new InetSocketAddress("127.0.0.1", port);
			HttpClientContext context = HttpClientContext.create();
			context.setAttribute("socks.address", socksaddr);

			HttpClient httpClient = getNewHttpClient();

			HttpGet httpGet = new HttpGet(new URI(uri));
			HttpResponse httpResponse = httpClient.execute(httpGet, context);
			HttpEntity httpEntity = httpResponse.getEntity();
			InputStream httpResponseStream = httpEntity.getContent();

			//Reads the whole content because I had nothing better to do and followed a well documented example
			BufferedReader httpResponseReader = new BufferedReader(
					new InputStreamReader(httpResponseStream, "iso-8859-1"), 8);
			String line = null;

			while ((line = httpResponseReader.readLine()) != null) {
				result += line;
			}
			httpResponseStream.close();

			return result;
		} catch (Exception ex) {
			//TODO : Catch exception properly
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onPreExecute() {

	}


	//Variables come here

	@Override
	protected void onPostExecute(String result) {

	}

	public boolean isConnected() {
		return isConnected;
	}

	//Talks for itself
	static class FakeDnsResolver implements DnsResolver {
		@Override
		public InetAddress[] resolve(String host) throws UnknownHostException {
			return new InetAddress[]{InetAddress.getByAddress(new byte[]{1, 1, 1, 1})};
		}
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




