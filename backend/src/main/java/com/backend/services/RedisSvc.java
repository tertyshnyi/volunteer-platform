package com.backend.services;

import com.backend.models.dto.UserConfidentialDTO;
import com.backend.models.enums.RedisKeyPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Service class for interacting with Redis cache to store and retrieve data.
 * This service provides methods to save, retrieve, and delete objects from Redis,
 * as well as specifically handling user confidential data.
 */
@Service
public class RedisSvc {

//    public static final TimeUnit CACHE_TIME_UNIT = TimeUnit.MINUTES;
//    public static final int USER_CACHE_LIFETIME = 10;
//
//    // Redis template to interact with Redis
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
    /**
     * Saves a value in the Redis cache with a key formed by the provided prefix and suffix.
     * The value will be stored for a defined period (USER_CACHE_LIFETIME).
     *
     * @param prefix the prefix of the cache key.
     * @param suffix the suffix of the cache key.
     * @param value  the value to be saved in the cache.
     */
//    public void save(RedisKeyPrefix prefix, String suffix, Object value) {
//        redisTemplate.opsForValue().set(prefix.getKey() + suffix, value, USER_CACHE_LIFETIME, CACHE_TIME_UNIT);
//    }

    /**
     * Retrieves a value from the Redis cache using the provided key prefix and suffix.
     *
     * @param prefix      the prefix of the cache key.
     * @param suffix      the suffix of the cache key.
     * @param objectClass the class type of the expected value.
     * @param <T>         the type of the object.
     * @return the value from the cache or null if not found or a casting error occurs.
     */
//    public <T> T find(RedisKeyPrefix prefix, String suffix, Class<T> objectClass) {
//        String key = prefix.getKey() + suffix;
//        try {
//            Object cache = redisTemplate.opsForValue().get(key);
//            if (cache != null) {
//                return objectClass.cast(cache);
//            }
//        } catch (ClassCastException ignored) {}
//        return null;
//    }
    /**
     * Saves a {@link UserConfidentialDTO} object in the Redis cache, using the user's email as the key.
     *
     * @param user the user data to store in the cache.
     */
//    public void saveUserConfidentialDto(UserConfidentialDTO user) {
//        if (user != null && user.getEmail() != null) {
//            save(RedisKeyPrefix.USER_CONFIDENTIAL, user.getEmail(), user);
//        }
//    }
    /**
     * Deletes an entry from the Redis cache using the provided key prefix and suffix.
     *
     * @param prefix the prefix of the cache key.
     * @param suffix the suffix of the cache key.
     */
//    public void delete(RedisKeyPrefix prefix, String suffix) {
//        redisTemplate.delete(prefix.getKey() + suffix);
//    }
}