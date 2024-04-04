package word.shelf.util.exceptions;

public class PersonAlreadyExistException extends RuntimeException {
    public PersonAlreadyExistException() {
        super("Person already exists.");
    }
}
