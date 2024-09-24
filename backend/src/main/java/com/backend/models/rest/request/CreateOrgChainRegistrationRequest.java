package com.backend.models.rest.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrgChainRegistrationRequest {

    private String name;

    public boolean isComplete(){
        return name != null;
    }
}
