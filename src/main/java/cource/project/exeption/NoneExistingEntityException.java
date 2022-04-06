package cource.project.exeption;

public class NoneExistingEntityException extends Exception {

    public NoneExistingEntityException() {
    }

    public NoneExistingEntityException(String message) {
        super(message);
    }

    public NoneExistingEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoneExistingEntityException(Throwable cause) {
        super(cause);
    }
}
