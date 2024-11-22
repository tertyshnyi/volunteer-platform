package com.backend.models.dto;

import com.backend.models.enums.UserAuthority;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Data Transfer Object (DTO) for representing a user's authority (role).
 *
 * This class is used to transfer data about a specific user authority, such as the role name, its weight, and
 * its corresponding authority string. It is commonly used when sending information related to user roles
 * or permissions between different layers of the application or to the client.
 */
@NoArgsConstructor
@Getter
@Setter
public class UserAuthorityDTO implements Serializable {
    private String authority;
    private String ukName;
    private int weight;

    public UserAuthorityDTO(UserAuthority userAuthority) {
        this.authority = userAuthority.getAuthority();
        this.ukName = userAuthority.getRole();
        this.weight = userAuthority.getWeight();
    }
}
