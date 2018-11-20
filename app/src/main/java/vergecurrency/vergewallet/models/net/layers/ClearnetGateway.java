package vergecurrency.vergewallet.models.net.layers;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class ClearnetGateway extends AsyncTask<String, Void, JSONObject> {

    private Context context;

    public ClearnetGateway(Context context) {
        this.context = context;
    }


    @Override
    protected JSONObject doInBackground(String... url) {
        try {

            HttpGet httppost = new HttpGet(url[0]);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(httppost);

            // StatusLine stat = response.getStatusLine();
            int status = response.getStatusLine().getStatusCode();

            if (status == 200) {
                HttpEntity entity = response.getEntity();
                String data = EntityUtils.toString(entity);


                return new JSONObject(data);
            }
            else return new JSONObject("error:\"no data available\"");

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
}
