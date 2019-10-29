package vergecurrency.vergewallet.service.model.wallet;

public class TxProposalErrorResponse {

	private String code;
	private String message;

	enum Error {
		BAD_SIGNATURES
	}

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
