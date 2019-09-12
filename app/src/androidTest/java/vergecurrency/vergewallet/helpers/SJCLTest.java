package vergecurrency.vergewallet.helpers;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import static org.junit.Assert.*;

public class SJCLTest {


	@Test
	public void base64ToBits() {
		SJCL sjcl = new SJCL(InstrumentationRegistry.getInstrumentation().getTargetContext());

		String result = sjcl.base64ToBits("dGVzdA==");
		assertTrue(result.equals("1.952805748E9")); // well... kind of. To be corrected.
	}
}