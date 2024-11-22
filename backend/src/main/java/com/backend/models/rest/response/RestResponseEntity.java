package com.backend.models.rest.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * A custom ResponseEntity class that extends Spring's {@link ResponseEntity} to encapsulate
 * the response body using the {@link RestResponseBody} structure.
 *
 * This class allows for more convenient creation of response entities with standardized response bodies
 * and is useful for returning consistent API responses, including success/failure status, messages, and data.
 *
 * @param <T> The type of data that will be returned in the response.
 */
public class RestResponseEntity<T> extends ResponseEntity<RestResponseBody<T>> {

    public RestResponseEntity(RestResponseBody<T> body, HttpStatus status) {
        super(body, status);
    }

    public RestResponseEntity(boolean success, String message, T data, HttpStatus status) {
        super(new RestResponseBody<>(success, message, data), status);
    }

    public RestResponseEntity(T data, HttpStatus status) {
        super(new RestResponseBody<>(true, "Success", data), status);
    }

    public RestResponseEntity(String message, HttpStatus status) {
        super(new RestResponseBody<>(false, message, null), status);
    }
}

