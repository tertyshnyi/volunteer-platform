package com.backend.models.rest.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

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
