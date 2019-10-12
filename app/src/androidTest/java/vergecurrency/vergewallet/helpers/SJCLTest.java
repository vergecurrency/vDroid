package vergecurrency.vergewallet.helpers;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class SJCLTest {
	private SJCL sjcl;

	public SJCLTest() {
		sjcl = new SJCL(InstrumentationRegistry.getInstrumentation().getTargetContext());
		sjcl.init();

	}

	@Test
	public void base64ToBitsTest() {
		String result = sjcl.base64ToBits("dGVzdA==");
		assertEquals("1952805748", result);
	}

	@Test
	public void encryptAndDecryptTest() {
		String encoded = sjcl.encrypt("Happy", "Friday", new int[] {2048,256});
		String decoded = sjcl.decrypt("Happy", encoded);

		assertEquals("Friday",decoded);
	}


	@Test
	public void sha256HastTest(){
		int[] hash = sjcl.sha256Hash("Hello Friday");

		assertArrayEquals(new int[]{118124657,-117793667,97360985,-421688632,-1022390789,506062141,-1117669270,-704988805},hash);
	}

	@Test
	public void hexFromBitsTest() {
		int[] hash = sjcl.sha256Hash("Hello Friday");
		String hex = sjcl.hexFromBits(hash);

		assertEquals("070a7071f8fa9c7d05cd9c59e6dd8ac8c30f8dfb1e29e53dbd61b86ad5fab97b", hex);
	}


}