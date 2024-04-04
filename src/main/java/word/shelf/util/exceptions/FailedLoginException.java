package word.shelf.util.exceptions;

public class FailedLoginException extends RuntimeException {
    public FailedLoginException() {
        super("Credentials are wrong");
    }
}
