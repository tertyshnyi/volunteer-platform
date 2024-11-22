package com.backend.models.dto;

import com.backend.models.entity.User;
import com.backend.models.enums.UserAuthority;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) representing the details of a user.
 *
 * This DTO is used for transferring user data, including the user's personal information,
 * organizational affiliation, and roles (authorities). It is typically used for providing user details
 * in API responses, particularly in cases where user data needs to be exposed to authenticated or authorized users.
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDTO implements Serializable {
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phoneNumber;
    private long createdAt;

    private UUID organizationId;
    private Collection<UserAuthority> authorities;

    public UserDTO(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.phoneNumber = user.getPhoneNumber();
        this.createdAt = user.getCreatedAt();

        if (user.getOrganization() != null) {
            this.organizationId = user.getOrganization().getId();
        }

        this.authorities = user.getAuthorities();
    }
}
