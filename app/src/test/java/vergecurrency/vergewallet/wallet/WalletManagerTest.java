package vergecurrency.vergewallet.wallet;



import org.testng.annotations.Test;

public class WalletManagerTest extends junit.framework.TestCase{

	@Test
	public void generateSeed() {

		WalletManager wm = new WalletManager();
		String[] mnemo = wm.generateMnemonic();

		for (String a: mnemo) {
			System.out.println(a);
		}


		assertTrue(mnemo instanceof String[]);
	}

}