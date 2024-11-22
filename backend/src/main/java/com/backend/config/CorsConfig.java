package com.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;

/**
 * Configuration class for setting up CORS (Cross-Origin Resource Sharing) and static resource handling.
 *
 * This class implements the `WebMvcConfigurer` interface to provide custom configuration for handling
 * cross-origin requests and static resources like the favicon. It configures the allowed origins,
 * HTTP methods, headers, and credentials for different API endpoint categories, ensuring secure and
 * controlled access to the backend API.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * Configures CORS mappings for API endpoints.
     *
     * This method allows the API to handle cross-origin requests by defining CORS rules:
     * - For public API endpoints (`/api/v1/public/**`), all origins are allowed for `OPTIONS`, `GET`,
     *   and `POST` methods, with no credentials.
     * - For secure API endpoints (`/api/v1/secure/**`), only requests from `http://localhost:8080`
     *   are allowed, with support for additional HTTP methods (`PUT`, `DELETE`) and credentials allowed.
     *
     * CORS headers are automatically included in responses to control access from different domains.
     *
     * @param registry the CORS registry used to define CORS mappings.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // CORS configuration for public API endpoints
        registry.addMapping("/api/v1/public/**")
                .allowedOrigins("*")
                .allowedMethods("OPTIONS", "GET", "POST")
                .allowedHeaders("*")
                .allowCredentials(false);
        // CORS configuration for secure API endpoints
        registry.addMapping("/api/v1/secure/**")
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    /**
     * Configures resource handling for static resources.
     *
     * This method maps the `/favicon.ico` path to the location of the favicon in the `static` directory
     * and sets cache control for the favicon to maximize caching for 7 days.
     *
     * @param registry the resource handler registry used to define static resource mappings.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Mapping for the favicon.ico
        registry.addResourceHandler("/favicon.ico")
                .addResourceLocations("classpath:/static/favicon.ico")
                .setCacheControl(CacheControl.maxAge(Duration.ofDays(7)))
        ;
    }
}
