package com.backend.models.dto;

import com.backend.models.entity.UserConfidential;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
public class UserConfidentialDTO implements Serializable {
    private UUID id;
    private String email;
    private Set<UserAuthorityDTO> authorities;
    private Long lastSeenAt;
    private boolean isOnline;
    private int level;

    public UserConfidentialDTO(UserConfidential userConfidential) {
        this.id = userConfidential.getId();
        this.email = userConfidential.getEmail();
        this.authorities = userConfidential.getAuthorities().stream()
                .map(UserAuthorityDTO::new)
                .collect(Collectors.toSet());
        this.lastSeenAt = userConfidential.getLastSeenAt();
        this.isOnline = userConfidential.isOnline();
        this.level = userConfidential.getLevel();
    }
}
