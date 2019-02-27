package vergecurrency.vergewallet.wallet;



import org.testng.annotations.Test;

import java.util.List;

import static org.junit.Assert.*;

public class WalletManagerTest extends junit.framework.TestCase{

	@Test
	public void generateSeed() {

		WalletManager wm = new WalletManager();
		String[] mnemo = wm.generateSeed();

		for (String a: mnemo) {
			System.out.println(a);
		}


		assertTrue(mnemo instanceof String[]);
	}

}