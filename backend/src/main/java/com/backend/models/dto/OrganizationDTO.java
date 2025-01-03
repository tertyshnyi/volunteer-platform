package com.backend.models.dto;

import com.backend.models.entity.Organization;
import com.backend.models.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Data Transfer Object (DTO) for representing an organization.
 *
 * This class is used to transfer data about an organization between different layers of the application,
 * such as from the service layer to the controller or to the client. It contains basic information about the
 * organization including its ID, name, contact details, and associated managers.
 */
@Getter
@Setter
@NoArgsConstructor
public class OrganizationDTO implements Serializable {

    private UUID id;
    private String name;
    private String country;
    private String city;
    private String address;
    private String postcode;
    private String number;
    private String email;
    private long createdAt;

    private Set<UUID> managerIds;
    private UUID chainId;

    public OrganizationDTO(Organization organization){
        this.id = organization.getId();
        this.name = organization.getName();
        this.country = organization.getCountry();
        this.city = organization.getCity();
        this.address = organization.getAddress();
        this.postcode = organization.getPostcode();
        this.number = organization.getNumber();
        this.email = organization.getEmail();
        this.createdAt = organization.getCreatedAt();

        if (organization.getChain() != null) {
            this.chainId = organization.getChain().getId();
        }

        this.managerIds = organization.getManagers()
                .stream()
                .map(User::getId)
                .collect(Collectors.toSet());
    }
}
