package com.backend.models.rest.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * This class represents a request to create a new user.
 * It includes the necessary details required to register a user, such as their name, surname, email,
 * password, and phone number.
 *
 * The request contains the following fields:
 * - `name`: The first name of the user.
 * - `surname`: The last name of the user.
 * - `email`: The email address of the user.
 * - `password`: The password chosen by the user.
 * - `phoneNumber`: The contact phone number of the user.
 *
 * The `isComplete()` method is provided to ensure that all required fields are filled before the request is processed.
 * If any field is missing, the request will be considered incomplete.
 */
@Getter
@Setter
public class CreateUserRequest {

    private String name;
    private String surname;
    private String email;
    private String password;
    private String phoneNumber;

    public boolean isComplete(){
        return name != null && surname != null && email != null && password != null && phoneNumber != null;
    }
}
