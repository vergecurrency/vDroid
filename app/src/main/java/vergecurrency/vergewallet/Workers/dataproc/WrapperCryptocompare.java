package vergecurrency.vergewallet.Workers.dataproc;
import org.json.JSONException;
import org.json.JSONObject;

public class WrapperCryptocompare {

   public WrapperCryptocompare(String currency) {
    }

    public String getVergeToFiat(String currency) {


        try {
            JSONObject reader = new JSONObject(uri + currency);
            return reader.getJSONObject("RAW").getJSONObject("XVG").getJSONObject(currency).getString("PRICE");
        } catch (JSONException e) {
            e.printStackTrace();
            return "error";
        }

    }

    private String cryptocompareUri;
    private String uri = "https://min-api.cryptocompare.com/data/pricemultifull?fsyms=XVG&tsyms=";
    private String currency = "USD";
}
