package com.odeyalo.sonata.artists.exception;

/**
 * This exception can be thrown when there is a problem with artist registration, such as
 * invalid data, database errors, or other registration-related issues.
 */
public class RegistrationException extends RuntimeException {

    public RegistrationException(String message) {
        super(message);
    }

    public RegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
