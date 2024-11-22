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

/**
 * Entity representing an organization chain.
 *
 * This class maps to the "organization_chain" table in the database and contains details about an organization chain,
 * including its name and a set of organizations that belong to the chain.
 * The entity defines a one-to-many relationship with the Organization entity and provides methods for adding and removing organizations.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrganizationChain {

    @Id
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid4")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, length = 32, unique = true)
    private String name;

    @OneToMany(mappedBy = "chain", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Organization> organizations = new HashSet<>();

    public OrganizationChain(String name) {
        this.name = name;
    }

    public void addOrganization(Organization organization) {
        if (organization != null && !organizations.contains(organization)) {
            organizations.add(organization);
            organization.setChain(this);
        }
    }

    public void removeOrganization(Organization organization) {
        if (organization != null && organizations.contains(organization)) {
            organizations.remove(organization);
            organization.setChain(null);
        }
    }
}
