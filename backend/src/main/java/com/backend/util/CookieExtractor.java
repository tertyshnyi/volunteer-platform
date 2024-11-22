package com.backend.util;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Utility class for extracting cookies or their values from an array of cookies.
 * This class is designed to search for a specific cookie (e.g., for storing JWT tokens) and extract it.
 * It is annotated with @Component to be recognized as a Spring-managed bean, making it injectable into other components if needed.
 */
@Component
public class CookieExtractor {

    /**
     * Extracts a specific cookie by its name from an array of cookies.
     *
     * @param cookies the array of cookies sent with the HTTP request.
     * @param key the name of the cookie to be extracted (e.g., a JWT cookie name).
     * @return the cookie matching the specified name, or null if not found.
     */
    public static Cookie extract(Cookie[] cookies, String key) {
        if (cookies == null) {
            return null;
        }
        return Arrays.stream(cookies)
                .filter(cookie -> JwtTokenUtil.JWT_COOKIE_KEY.equals(cookie.getName()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Extracts the value of a specific cookie by its name from an array of cookies.
     *
     * @param cookies the array of cookies sent with the HTTP request.
     * @param key the name of the cookie whose value is to be extracted.
     * @return the value of the cookie if found, or null if the cookie is not present or has no value.
     */
    public static String extractValue(Cookie[] cookies, String key) {
        if (cookies == null) {
            return null;
        }
        Cookie cookie = Arrays.stream(cookies)
                .filter(c -> JwtTokenUtil.JWT_COOKIE_KEY.equals(c.getName()))
                .findFirst()
                .orElse(null);
        return cookie != null ? cookie.getValue() : null;
    }
}
