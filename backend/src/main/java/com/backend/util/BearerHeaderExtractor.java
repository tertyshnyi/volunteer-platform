package com.backend.util;

import org.springframework.stereotype.Component;

/**
 * Utility class to extract the token from an Authorization header.
 * This class is designed to work with HTTP headers that follow the "Bearer <token>" format.
 * The class is annotated with @Component to be recognized as a Spring-managed bean,
 * allowing it to be injected into other components or services if necessary.
 */
@Component
public class BearerHeaderExtractor {

    /**
     * Extracts the token from a Bearer token Authorization header.
     *
     * @param header the Authorization header that contains the Bearer token.
     * @return the token extracted from the header, or null if the header is invalid or does not start with "Bearer ".
     */
    public static String extract(String header) {
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
