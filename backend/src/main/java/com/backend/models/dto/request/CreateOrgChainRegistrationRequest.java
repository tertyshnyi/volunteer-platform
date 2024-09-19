package com.backend.models.dto.request;

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
