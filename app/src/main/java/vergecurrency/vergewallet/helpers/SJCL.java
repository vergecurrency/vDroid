package vergecurrency.vergewallet.helpers;

import android.content.Context;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;

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

		V8Object count = new V8Object(runtime).add("count", params[0]);
		V8Object ks = new V8Object(runtime).add("ks",params[1]);

		V8Array paramsArray = new V8Array(runtime).push(count).push(ks);

		Object result = sjcl.executeJSFunction("encrypt",password,message,new V8Array(runtime).push(paramsArray));

		count.close();
		ks.close();
		paramsArray.close();


		return result.toString();

	}

	public String decrypt(String password, String message) {

		V8Object param1 = new V8Object(runtime).add("param1", password);
		V8Object param2 = new V8Object(runtime).add("param2", message);

		V8Array params = new V8Array(runtime).push(param1).push(param2);

		Object result = sjcl.executeJSFunction("decrypt",params);

		param1.close();
		param2.close();
		params.close();

		return result.toString();
	}

	public String base64ToBits(String encryptingKey) {
		V8Object base64 = sjcl.getObject("codec").getObject("base64");

		Object result = base64.executeJSFunction("toBits",encryptingKey);

		base64.release();

		return result.toString();
	}
}
