package ua.skidchenko.touristic_agency.exceptions;

public class UsernameExistsException extends RuntimeException{
    public UsernameExistsException() {
        super();
    }

    public UsernameExistsException(String message) {
        super(message);
    }

    public UsernameExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
