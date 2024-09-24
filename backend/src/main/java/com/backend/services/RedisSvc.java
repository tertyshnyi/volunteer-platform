package com.backend.services;

import com.backend.models.dto.UserConfidentialDTO;
import com.backend.models.enums.RedisKeyPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisSvc {

//    public static final TimeUnit CACHE_TIME_UNIT = TimeUnit.MINUTES;
//    public static final int USER_CACHE_LIFETIME = 10;
//
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
//
//    public void save(RedisKeyPrefix prefix, String suffix, Object value) {
//        redisTemplate.opsForValue().set(prefix.getKey() + suffix, value, USER_CACHE_LIFETIME, CACHE_TIME_UNIT);
//    }
//
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
//
//    public void saveUserConfidentialDto(UserConfidentialDTO user) {
//        redisTemplate.opsForValue();
//    }
//
//    public void delete(RedisKeyPrefix prefix, String suffix) {
//        redisTemplate.delete(prefix + suffix);
//    }
}