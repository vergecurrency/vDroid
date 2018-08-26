package vergecurrency.vergewallet.models.dataproc;
import org.json.JSONException;
import org.json.JSONObject;

public class ParserCryptocompare {

   public ParserCryptocompare(String currency) {
    }

    public String getVergeToFiat(String currency) {

        try {
            JSONObject reader = new JSONObject(URI + currency);
            return reader.getJSONObject("RAW").getJSONObject("XVG").getJSONObject(currency).getString("PRICE");
        } catch (JSONException e) {
            e.printStackTrace();
            return "error";
        }

    }
    private String currency = "USD";

    private final static String URI = "https://min-api.cryptocompare.com/data/pricemultifull?fsyms=XVG&tsyms=";
}
