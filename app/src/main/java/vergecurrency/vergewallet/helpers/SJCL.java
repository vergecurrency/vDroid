package vergecurrency.vergewallet.helpers;

import android.content.Context;

import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;

import java.io.InputStream;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import vergecurrency.vergewallet.Constants;
import vergecurrency.vergewallet.helpers.utils.FileUtils;

public class SJCL {

	private ScriptEngine engine;
	private org.mozilla.javascript.Context jsContext;

	public SJCL(Context c) {

		init(c);
	}

	public void init(Context c) {
		ScriptEngineManager manager = new ScriptEngineManager();
		engine = manager.getEngineByName("rhino");
		try {
			InputStream is = c.getAssets().open(Constants.SJCL_FILE_PATH);
			String sjcl = FileUtils.convertStreamToString(is);

			// read script file
			engine.eval(sjcl);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Can't do something..................................................");
		}
	}

	public String encrypt(Object password, String plainText, String[] params) {
		try {
			Invocable invocable = (Invocable) engine;

			return invocable.invokeFunction("sjcl.encrypt", password, plainText, params).toString();
		} catch (ScriptException | NoSuchMethodException e) {
			return null;
		}
	}

	public String decrypt(Object password, String plainText, String[] params) {
		try {
			Invocable invocable = (Invocable) engine;
			return invocable.invokeFunction("sjcl.decrypt", password, plainText, params).toString();
		} catch (ScriptException | NoSuchMethodException e) {
			return null;
		}
	}

	/**
	 * This method might look confusing. So here a detailed explanation.
	 * The SJCL Library has nested objects, sorry if it's not the correct phrasing, I'm no JS expert.
	 * So, while in JS (and in Swift too) you could call the "toBits" method by just typing "sjcl.codec.base64.toBits",
	 * well, you can't in Java because fuck doing things easily (thanks Mozilla tho). So, we have to
	 * use the ScriptEngine to get the SJCL object as a NativeObject, which is basically a key-value pair
	 * that will have as a key the object name and as values the sub-objects/methods.
	 * So I had to cast the returned objects as NativeObjects and retrieve the correct keys according to the file structure.
	 *
	 * The result will come as a native array whereas the 0 index contains the result, which is turned to a String.
	 *
	 * @param encryptingKey the key in a string form
	 * @return the string represantation of a bit array.
	 */
	public String base64ToBits(String encryptingKey) {
		try {
			Invocable invocable = (Invocable) engine;
			Object o = ((NativeObject) ((NativeObject) engine.get("sjcl")).get("codec")).get("base64");
			NativeArray o2 = (NativeArray) invocable.invokeMethod(o, "toBits", encryptingKey);

			//THIS IS STILL NOT WORKING AS EXPECTED.
			String result = o2.get(0).toString();
			return result;

		} catch (ScriptException | NoSuchMethodException e) {
			e.printStackTrace();
			return null;
		}
	}
}
