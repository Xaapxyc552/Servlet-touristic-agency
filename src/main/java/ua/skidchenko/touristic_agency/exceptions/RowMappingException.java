package ua.skidchenko.touristic_agency.exceptions;

public class RowMappingException extends RuntimeException {
    public RowMappingException() {
        super();
    }

    public RowMappingException(String message) {
        super(message);
    }

    public RowMappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
