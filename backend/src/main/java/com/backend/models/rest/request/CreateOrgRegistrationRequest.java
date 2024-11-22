package com.backend.models.rest.request;

import lombok.Getter;
import lombok.Setter;

/**
 * This class represents a request to register a new organization.
 * It contains the necessary details required to create a new organization, such as its name, country, city,
 * address, postcode, contact number, and email.
 *
 * The request includes the following fields:
 * - `name`: The name of the organization.
 * - `country`: The country where the organization is located.
 * - `city`: The city where the organization is located.
 * - `address`: The street address of the organization.
 * - `postcode`: The postal code of the organization's location.
 * - `number`: The contact phone number of the organization.
 * - `email`: The email address for contacting the organization.
 *
 * It also provides a utility method `isComplete()`, which checks if all required fields are provided and ensures
 * the validity of the request before processing.
 */
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
