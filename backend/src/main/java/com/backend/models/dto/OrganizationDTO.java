package com.backend.models.dto;

import com.backend.models.entity.Organization;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

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
    }
}
