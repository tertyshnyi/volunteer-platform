package com.backend.models.dto.request;

import com.backend.models.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateUserRequest {

    private UUID id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phoneNumber;
    private UserRole userRole;

    public boolean isComplete(){
        return name != null && surname != null && email != null && password != null && phoneNumber != null
                && userRole != null;
    }
}
