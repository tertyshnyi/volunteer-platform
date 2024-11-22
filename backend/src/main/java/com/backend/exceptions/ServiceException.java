package com.backend.exceptions;

/**
 * Custom exception class to handle service-related errors.
 *
 * This exception is thrown when a service encounters an error that prevents it from completing
 * its operation. It extends the built-in `Exception` class and provides a constructor to
 * pass an error message, which can be used to provide more context about the error.
 */
public class ServiceException extends Exception {
    /**
     * Constructs a new ServiceException with the specified detail message.
     *
     * This constructor allows the passing of a custom message, which can provide more details
     * about the exception, such as the cause or specific context of the error.
     *
     * @param message the detail message, which is saved for later retrieval by the {@link #getMessage()} method.
     */
    public ServiceException(String message) {
        super(message);
    }
}
