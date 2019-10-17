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


	public String encrypt(int[] key, String message, int[] params) {

		V8Array keyObj = new V8Array(runtime);
		for (int bitValue: key) {
			keyObj.push(bitValue);
		}

		V8Object paramsObj = new V8Object(runtime).add("ks", params[0]).add("iter", params[1]);

		V8Array paramsArray = new V8Array(runtime).push(keyObj).push(message).push(paramsObj);
		String result = sjcl.executeStringFunction("encrypt", paramsArray);

		keyObj.close();
		paramsObj.close();
		paramsArray.close();

		return result;
	}

	public String decrypt(int[] key, String message) {

		V8Array keyObj = new V8Array(runtime);
		for (int bitValue: key) {
			keyObj.push(bitValue);
		}

		String  result = sjcl.executeJSFunction("decrypt", keyObj, message.replace("\\", "")).toString();

		keyObj.close();

		return result;
	}

	public int[] base64ToBits(String encryptingKey) {
		V8Object base64 = sjcl.getObject("codec").getObject("base64");

		V8Array params = new V8Array(runtime).push(encryptingKey);
		V8Array result = base64.executeArrayFunction("toBits", params);

		base64.close();
		params.close();

		return result.getIntegers(0,result.length());
	}

	public int[] sha256Hash(String data) {
		V8Object sha256 = sjcl.getObject("hash").getObject("sha256");
		V8Array params = new V8Array(runtime).push(data);

		int[] result =  sha256.executeArrayFunction("hash", params).getIntegers(0,8);

		sha256.close();
		params.close();

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
