package vergecurrency.vergewallet.excpetion;

public class VergeException extends Exception {
    private VergeError error;

    public VergeException(VergeError error) {
        super(error.getMessage());
        this.error = error;
    }

    public VergeException(Exception e, VergeError error) {
        super(error.getMessage());
        setStackTrace(e.getStackTrace());
    }

    public VergeError getError() {
        return error;
    }
}
