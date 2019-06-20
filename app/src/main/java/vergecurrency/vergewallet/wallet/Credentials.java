package vergecurrency.vergewallet.wallet;


import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import io.horizontalsystems.hdwalletkit.HDKey;
import io.horizontalsystems.hdwalletkit.HDKeychain;
import io.horizontalsystems.hdwalletkit.HDPublicKey;
import kotlin.text.Charsets;
import vergecurrency.vergewallet.service.model.MnemonicManager;
import vergecurrency.vergewallet.service.model.PreferencesManager;

public class Credentials {

	private HDKeychain hdKeychain;
	public Credentials() {
		hdKeychain = initHDKeyChain();
	}
	public HDKeychain initHDKeyChain() {
		String[] mnemonic = MnemonicManager.getMnemonicFromJSON(PreferencesManager.getMnemonic());
		String passphrase = PreferencesManager.getPassphrase();

		byte[] seed = new io.horizontalsystems.hdwalletkit.Mnemonic().toSeed((List<String>) Arrays.asList(mnemonic), passphrase);
		return new HDKeychain(seed, true);
	}

	public HDKey getWalletPrivateKey() {
		//hardened at 0
		return hdKeychain.getKeyByPath("/0'");
	}

	public HDKey getRequestWalletPrivateKey() {
		return hdKeychain.getKeyByPath("/1'/0");
	}


	public HDKey getBip44PrivateKey() {
		return hdKeychain.getKeyByPath("m/44'/0'/0'");
	}

	public HDPublicKey getHDPublicKey() {
		return new HDPublicKey(0, false, getBip44PrivateKey());
	}

	public String getPersonalEncryptingKey() {
		try {
			byte[] data = MessageDigest.getInstance("SHA-256").digest(getRequestWalletPrivateKey().getPrivKeyBytes());
			byte[] key = "personalKey".getBytes(Charsets.UTF_8);

			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(new SecretKeySpec(key, "HmacSHA256"));
			return Base64.getEncoder().encodeToString(Arrays.copyOf(mac.doFinal(data), 16));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getSharedEncryptingKey() {
		try {
			byte [] data = MessageDigest.getInstance("SHA-256").digest(getWalletPrivateKey().getPrivKeyBytes());
			return Base64.getEncoder().encodeToString(Arrays.copyOf(data, 16));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
