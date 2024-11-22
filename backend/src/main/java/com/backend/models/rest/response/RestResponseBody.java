package com.backend.models.rest.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A generic class that represents the response body structure for REST API responses.
 * This class encapsulates the response details, including the success status, a message,
 * and any data that might be returned as part of the response.
 *
 * @param <T> The type of data returned in the response.
 */
@Getter
@Setter
@ToString
public class RestResponseBody<T> {
    private long timestamp;
    private boolean success;
    private String message;
    private T data;

    public RestResponseBody() {
        this.timestamp = System.currentTimeMillis();
    }

    public RestResponseBody(boolean success, String message, T data) {
        this.timestamp = System.currentTimeMillis();
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
