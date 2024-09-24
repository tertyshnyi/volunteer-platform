package com.backend.models.dto;

import com.backend.models.enums.UserAuthority;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

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
