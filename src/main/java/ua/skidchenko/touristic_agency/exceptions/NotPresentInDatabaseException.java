package ua.skidchenko.touristic_agency.exceptions;


public class NotPresentInDatabaseException extends RuntimeException {
    public NotPresentInDatabaseException() {
        super();
    }

    public NotPresentInDatabaseException(String message) {
        super(message);
    }

    public NotPresentInDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }


}
