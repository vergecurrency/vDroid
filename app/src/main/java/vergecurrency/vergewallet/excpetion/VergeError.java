package vergecurrency.vergewallet.excpetion;


public class VergeError {
    public static final VergeError FAILED_TO_GENERATE_SEED = new VergeError(101, "Failed to generate wallet seed");

    private VergeError(int code, String message){
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    protected int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    protected String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
