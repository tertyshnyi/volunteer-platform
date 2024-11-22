package com.backend.models.rest.request;

import lombok.Getter;
import lombok.Setter;

/**
 * This class represents a request to create a login for a user, including their email or phone number
 * and the password. It is used as part of the request payload for login operations in the backend.
 *
 * The request contains two fields:
 * - `emailOrPhoneNumber`: A string that holds the user's email or phone number (used to identify the user).
 * - `password`: A string that holds the user's password.
 *
 * It also provides a utility method, `isComplete()`, to check if both fields are provided (i.e., not null),
 * ensuring that the login request is valid before processing.
 */
@Getter
@Setter
public class CreateLoginRequest {
    private String emailOrPhoneNumber;
    private String password;

    public boolean isComplete() {
        return emailOrPhoneNumber != null && password != null;
    }
}
