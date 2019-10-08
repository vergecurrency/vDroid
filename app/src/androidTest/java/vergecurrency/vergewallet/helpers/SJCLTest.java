package vergecurrency.vergewallet.helpers;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SJCLTest {
	private SJCL sjcl;

	public SJCLTest() {
		sjcl = new SJCL(InstrumentationRegistry.getInstrumentation().getTargetContext());
		sjcl.init();

	}

	@Test
	public void base64ToBits() {
		String result = sjcl.base64ToBits("dGVzdA==");
		assertEquals("1952805748", result);
	}

	@Test
	public void encryptAndDecrypt() {
		String encoded = sjcl.encrypt("Happy", "Friday", new int[] {2048,256});
		//String decoded = sjcl.decrypt("Happy", encoded);

		assertEquals(true, encoded != null);
	}
}