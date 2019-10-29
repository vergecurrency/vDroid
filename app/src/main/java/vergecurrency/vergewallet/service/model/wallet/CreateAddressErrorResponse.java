package vergecurrency.vergewallet.service.model.wallet;

public class CreateAddressErrorResponse {

	enum Error {
		MAIN_ADDRESS_GAP_REACHED
	}

	private String code;
	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
