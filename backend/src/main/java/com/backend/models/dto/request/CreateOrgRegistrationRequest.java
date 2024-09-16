package com.backend.models.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrgRegistrationRequest {

    private String name;
    private String country;
    private String city;
    private String address;
    private String postcode;
    private String number;
    private String email;

    public boolean isComplete(){
        return name != null && country != null && city != null && address != null && postcode != null
                && number != null && email != null;
    }
}
