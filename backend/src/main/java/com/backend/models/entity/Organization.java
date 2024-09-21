package com.backend.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="organization")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Organization {

    @Id
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid4")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, length = 32, unique = true)
    private String name;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String postcode;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "chain_id")
    private OrganizationChain chain;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
    private Set<User> managers = new HashSet<>();

    private long createdAt;

    public void addUser(User user) {
        if (user != null && !managers.contains(user)) {
            managers.add(user);
            user.setOrganization(this);
        }
    }

    public void removeUser(User user) {
        if (user != null && managers.contains(user)) {
            managers.remove(user);
            user.setOrganization(null);
        }
    }

}
