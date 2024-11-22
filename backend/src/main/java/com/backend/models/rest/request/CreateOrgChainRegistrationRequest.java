package com.backend.models.rest.request;

import lombok.Getter;
import lombok.Setter;

/**
 * This class represents a request to register a new organization chain.
 * It contains information required to create a new organization chain, specifically the name of the chain.
 *
 * The request includes the following field:
 * - `name`: The name of the organization chain to be registered.
 *
 * It also provides a utility method `isComplete()`, which checks whether the required field (`name`) is provided,
 * ensuring that the request is valid before processing.
 */
@Getter
@Setter
public class CreateOrgChainRegistrationRequest {

    private String name;

    public boolean isComplete(){
        return name != null;
    }
}
