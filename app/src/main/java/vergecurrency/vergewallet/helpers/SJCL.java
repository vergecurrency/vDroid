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
	// TODO : I JUST BROKE IT. NEED REPAIR, BUT I AM HUNGRY.
	public String encrypt(String password, String message, int[] params) {

		V8Object paramsObj = new V8Object(runtime).add("ks", params[0]).add("iter", params[1]);
		V8Array paramsArray = new V8Array(runtime).push(password).push(message).push(paramsObj);
		String result = sjcl.executeStringFunction("encrypt", paramsArray);

		paramsObj.close();
		paramsArray.close();

		return result;
	}

	public String decrypt(String password, String message) {
		return sjcl.executeJSFunction("decrypt", password, message.replace("\\", "")).toString();
	}

	public String base64ToBits(String encryptingKey) {
		V8Object base64 = sjcl.getObject("codec").getObject("base64");

		Object result = base64.executeJSFunction("toBits", encryptingKey);

		base64.close();

		return result.toString();
	}

	public int[] sha256Hash(String data) {
		V8Object sha256 = sjcl.getObject("hash").getObject("sha256");
		V8Array params = new V8Array(runtime).push(data);

		int[] result =  sha256.executeArrayFunction("hash", params).getIntegers(0,8);

		sha256.close();

		return result;
	}

	public String hexFromBits(int[] hash){
		V8Object hex = sjcl.getObject("codec").getObject("hex");
		V8Array params = new V8Array(runtime);
		for (int val: hash) {
			params.push(val);
		}

		Object result = hex.executeJSFunction("fromBits", params);

		hex.close();
		params.close();

		return result.toString();

	}

}
