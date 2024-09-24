package com.backend.models.rest.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

