package vergecurrency.vergewallet.wallet;


import io.horizontalsystems.bitcoinkit.exceptions.AddressFormatException;
import io.horizontalsystems.bitcoinkit.network.MainNet;
import io.horizontalsystems.bitcoinkit.utils.AddressConverter;

public class AddressValidator {

	public static boolean isValidAddress(String address) {
		try {
			new AddressConverter(new MainNet()).convert(address);
			return true;
		} catch (AddressFormatException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	public ValidationCompletion validate(String address) {
		ValidationCompletion vc = new ValidationCompletion(false, "", 0f);
		if (AddressValidator.isValidAddress(address)) {
			vc.setValid(true);
			vc.setAddress(address);
		}

		String splittedAddress[] = address.replace("verge://", "").replace("verge:", "").split("'?");
	}


	//inner struct
	public class ValidationCompletion {
		boolean valid;
		String address;
		Float amount;

		public ValidationCompletion(boolean valid, String address, Float amount) {
			this.valid = valid;
			this.address = address;
			this.amount = amount;
		}

		public boolean isValid() {
			return valid;
		}

		public void setValid(boolean valid) {
			this.valid = valid;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public Float getAmount() {
			return amount;
		}

		public void setAmount(Float amount) {
			this.amount = amount;
		}
	}
}
