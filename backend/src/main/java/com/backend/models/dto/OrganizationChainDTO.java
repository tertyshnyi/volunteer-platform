package com.backend.models.dto;

import com.backend.models.entity.OrganizationChain;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Data Transfer Object (DTO) for representing an organization chain.
 *
 * This class is used to transfer data about an organization chain between application layers.
 * It includes the chain's identifier, name, and the IDs of the organizations that are part of this chain.
 * The class is serializable, allowing it to be used in contexts like HTTP responses.
 */
@Getter
@Setter
@NoArgsConstructor
public class OrganizationChainDTO implements Serializable {

    private UUID id;
    private String name;
    private Set<UUID> organizationIds;

    public OrganizationChainDTO(OrganizationChain chain) {
        this.id = chain.getId();
        this.name = chain.getName();
        this.organizationIds = chain.getOrganizations()
                .stream()
                .map(org -> org.getId())
                .collect(Collectors.toSet());
    }
}
