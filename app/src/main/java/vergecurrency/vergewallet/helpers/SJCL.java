package vergecurrency.vergewallet.helpers;

import android.content.Context;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

import vergecurrency.vergewallet.Constants;
import vergecurrency.vergewallet.helpers.utils.FileUtils;

public class SJCL {
	private Context c;
	private V8 runtime;
	private V8Object sjcl;

	public SJCL(Context c) {
		this.c = c;
	}

	public void init() {
		try {

			//get the script
			InputStream is = c.getAssets().open(Constants.SJCL_FILE_PATH);
			String sjclFile = FileUtils.convertStreamToString(is);

			//init V8 Runtime
			runtime = V8.createV8Runtime();
			runtime.executeVoidScript(sjclFile);

			sjcl = runtime.getObject("sjcl");

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Can't do something..................................................");
		}
	}

	public String encrypt(String password, String message, int[] params) {

		V8Object paramsObj = new V8Object(runtime).add("count", params[0]).add("ks", params[1]);
		V8Array paramsArray = new V8Array(runtime).push(password).push(message).push(paramsObj);
		String result = sjcl.executeStringFunction("encrypt", paramsArray);

		paramsObj.close();
		paramsArray.close();

		return result;
	}

	public String decrypt(String password, String message) {
			return sjcl.executeJSFunction("decrypt",password,message.replace("\\","")).toString();
	}

	public String base64ToBits(String encryptingKey) {
		V8Object base64 = sjcl.getObject("codec").getObject("base64");

		Object result = base64.executeJSFunction("toBits", encryptingKey);

		base64.close();

		return result.toString();
	}
}
