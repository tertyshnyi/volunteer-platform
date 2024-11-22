package com.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Configuration class for setting up Redis integration with Spring.
 *
 * This class configures a `RedisTemplate` bean for interacting with Redis in a Spring application.
 * It specifies the serializers to use for keys and values to ensure proper serialization and deserialization
 * of data when storing and retrieving objects in Redis.
 */
@Configuration
public class RedisConfig {
    /**
     * Configures a RedisTemplate bean for interacting with Redis.
     *
     * This method sets up a `RedisTemplate` that uses the Redis connection factory provided by Spring.
     * It configures serializers to ensure proper handling of keys and values:
     * - The key serializer is set to `StringRedisSerializer`, which serializes keys as plain strings.
     * - The value serializer is set to `GenericJackson2JsonRedisSerializer`, which serializes values as JSON using Jackson.
     *
     * @param redisConnectionFactory the Redis connection factory used to create the connection to the Redis server.
     * @return a `RedisTemplate` configured with custom serializers for keys and values.
     */
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(redisConnectionFactory);
//
//        // Use Jackson 2 JSON serializer for values
//        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//
//        // Use String serializer for keys
//        template.setKeySerializer(new StringRedisSerializer());
//
//        return template;
//    }
}