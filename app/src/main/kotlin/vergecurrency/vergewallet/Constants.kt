package vergecurrency.vergewallet

object Constants {
    //Wallet constants
    const val FETCH_WALLET_TIMEOUT = 30.0
    const val FETCH_PRICE_TIMEOUT = 150
    const val SATOSHIS_DIVIDER = 1000000.0
    const val MINIMUM_FEE = 0.1
    const val NEEDED_CONFIRMATIONS = 1

    //Service Urls
    const val VERGE_WEBSITE = "https://vergecurrency.com"
    const val VDROID_REPO = "https://github.com/vergecurrency/vDroid"
    const val BLOCKCHAIN_EXPLORER = "https://verge-blockchain.info"
    //public final static String VWS_ENDPOINT = "https://vws2.swenvanzanten.com/vws/api";
    const val VWS_ENDPOINT = "https://vws.2lazy2.dev/vws/api"


    //Secondary Service Urls
    const val PRICE_DATA_ENDPOINT = "https://api.vergecurrency.network/price/api/v1/price/"
    const val CHART_DATA_ENDPOINT = "https://graphs2.coinmarketcap.com/currencies/verge/"
    const val IP_DATA_ENDPOINT = "http://api.ipstack.com/%s?access_key=7ad464757507e0b58ce0beee4810c1ab"
    const val IP_RETRIEVAL_ENDPOINT = "https://api.ipify.org?format=json"

    const val SEED_SIZE = 12
    const val WORDLIST_SIZE = 2048
    //var seed: List<String>? = null

    //Resource directories
    const val CURRENCIES_FILE_PATH = "currencies.json"
    const val LANGUAGES_FILE_PATH = "languages.json"
    const val MOCK_TRANSACTIONS_FILE_PATH = "transactions.json"
    const val SJCL_FILE_PATH = "SJCL.js"


    //public final static String CMC_API_KEY = "530a3244-7c12-4b13-88f2-3eed4127e2d7";

}

