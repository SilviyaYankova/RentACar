package cource.project.exeption;

public class NoneAvailableEntityException extends Exception {

    public NoneAvailableEntityException() {
    }

    public NoneAvailableEntityException(String message) {
        super(message);
    }

    public NoneAvailableEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoneAvailableEntityException(Throwable cause) {
        super(cause);
    }
}
