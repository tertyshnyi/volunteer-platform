package com.backend.models.dto;

import com.backend.models.entity.OrganizationChain;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
