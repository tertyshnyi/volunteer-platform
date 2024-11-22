package com.backend.models.enums;

import lombok.Getter;

/**
 * Enum representing the key prefixes used for storing data in Redis.
 *
 * Redis is often used for caching purposes, and this enum defines the key patterns
 * that are used to store different types of user data in the cache. Each prefix
 * is used to build the complete Redis key by appending an identifier (e.g., user ID
 * or email) to the prefix, forming unique keys for each type of data.
 */
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
