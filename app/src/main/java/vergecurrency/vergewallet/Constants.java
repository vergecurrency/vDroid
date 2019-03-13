package vergecurrency.vergewallet;

import java.util.ArrayList;
import java.util.List;

public final class Constants {

    //Wallet constants
    public final static Double FETCH_WALLET_TIMEOUT = 30d;
    public final static Double FETCH_PRICE_TIMEOUT = 150d;
    public final static Double SATOSHIS_DIVIDER = 1000000d;
    public final static Double MINIMUM_FEE = 0.1d;
    public final static int NEEDED_CONFIRMATIONS = 1;

    //Service Urls
    public final static String VERGE_WEBSITE = "https://vergecurrency.com";
    public final static String VDROID_REPO = "https://github.com/vergecurrency/vDroid";
    public final static String BLOCKCHAIN_EXPLORER = "https://verge-blockchain.info";
    public final static String VWS_ENDPOINT = "https://vws2.swenvanzanten.com/vws/api/";

    //Secondary Service Urls
    public final static String PRICE_DATA_ENDPOINT = "https://vws2.swenvanzanten.com/price/api/v1/price/";
    public final static String CHART_DATA_ENDPOINT  = "https://graphs2.coinmarketcap.com/currencies/verge/";
    public final static String IP_DATA_ENDPOINT = "http://api.ipstack.com/%s?access_key=7ad464757507e0b58ce0beee4810c1ab";
    public final static String IP_RETRIEVAL_ENDPOINT = "https://api.ipify.org?format=json";

    public final static int SEED_SIZE = 12;
    public final static int WORDLIST_SIZE = 2048;
    public static List<String> seed;

    //Resource directories
	public final static String CURRENCIES_FILE_PATH = "currencies.json";
	public final static String MOCK_TRANSACTIONS_FILE_PATH = "transactions.json";


	//public final static String CMC_API_KEY = "530a3244-7c12-4b13-88f2-3eed4127e2d7";

}
