package word.shelf.util.exceptions;

public class FailedDeleteException extends RuntimeException {
    public FailedDeleteException() {
        super("User was not deleted");
    }
}
