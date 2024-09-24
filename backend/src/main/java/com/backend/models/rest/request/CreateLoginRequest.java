package com.backend.models.rest.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateLoginRequest {
    private String emailOrPhoneNumber;
    private String password;

    public boolean isComplete() {
        return emailOrPhoneNumber != null && password != null;
    }
}
