package ua.skidchenko.touristic_agency.exceptions;

import java.util.Locale;
import java.util.ResourceBundle;

public class UserHasNoMoneyException extends RuntimeException {
    public UserHasNoMoneyException() {
        super();
    }

    public UserHasNoMoneyException(String message) {
        super(message);
    }

    public UserHasNoMoneyException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserHasNoMoneyException(Throwable cause) {
        super(cause);
    }

    protected UserHasNoMoneyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getLocalizedMessage(Locale messageLocale) {
        return ResourceBundle.getBundle("messages",messageLocale).getString("exception.user_has_no_money");
    }
}
