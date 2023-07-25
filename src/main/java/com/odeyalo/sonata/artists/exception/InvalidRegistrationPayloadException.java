package com.odeyalo.sonata.artists.exception;

/**
 * This exception is thrown when the provided artist registration payload is invalid or contains
 * missing or incorrect data during the artist registration process.
 */
public class InvalidRegistrationPayloadException extends RegistrationException {

    public InvalidRegistrationPayloadException(String message) {
        super(message);
    }

    public InvalidRegistrationPayloadException(String message, Throwable cause) {
        super(message, cause);
    }
}
