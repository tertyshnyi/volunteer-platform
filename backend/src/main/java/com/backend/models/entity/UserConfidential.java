package com.backend.models.entity;

import com.backend.models.enums.UserAuthority;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.Set;
import java.util.UUID;

/**
 * Entity representing confidential user information.
 *
 * This class maps to the "user" table in the database and stores sensitive information related to a user.
 * It contains the user's email, authorities (roles), last seen timestamp, online status, and security level.
 * This entity is used for securely managing user credentials and access.
 */
@Getter
@Setter
@Entity
@Table(name = "user")
public class UserConfidential {
    @Id
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid4")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, length = 64, unique = true)
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_authorities", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "authority")
    private Set<UserAuthority> authorities;

    private long lastSeenAt;
    private boolean isOnline;

    private int level;
}