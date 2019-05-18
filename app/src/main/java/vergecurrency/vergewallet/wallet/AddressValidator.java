package vergecurrency.vergewallet.wallet;


import android.provider.Telephony;

import java.util.HashMap;

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

		String[] splittedRequest = address.replace("verge://", "").replace("verge:", "").split("\\?");

		String [] parameters = splittedRequest[splittedRequest.length-1].split("&");

		if (AddressValidator.isValidAddress(splittedRequest[0])) {
			vc.setValid(true);
			vc.setAddress(splittedRequest[0]);
		} else {
			return vc;
		}

		String [] splittedParameters = parameters[parameters.length-1].split("&");

		HashMap<String,String> definitiveParameters = new HashMap<>();

		for (String param : splittedParameters) {
			String[] parameterPair = param.split("=");
			definitiveParameters.put(parameterPair[0], parameterPair[1]);
		}

		Float amount = Float.parseFloat(definitiveParameters.getOrDefault("amount","0"));

		vc.setAmount(amount);

		return vc;
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
