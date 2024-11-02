package com.backend.models.enums;

import lombok.Getter;

@Getter
public enum RedisKeyPrefix {
    USER_DTO_BY_ID("userdto.id."),
    USER_DTO_BY_EMAIL("userdto.email."),
    USER_CONFIDENTIAL("user.confidential."),
    ;

    private final String key;

    RedisKeyPrefix(String key) {
        this.key = key;
    }


}
