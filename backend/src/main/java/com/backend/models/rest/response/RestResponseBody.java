package com.backend.models.rest.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
