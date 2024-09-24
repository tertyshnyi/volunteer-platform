package com.backend.util;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CookieExtractor {
    public static Cookie extract(Cookie[] cookies, String key) {
        if (cookies == null) {
            return null;
        }
        return Arrays.stream(cookies)
                .filter(cookie -> JwtTokenUtil.JWT_COOKIE_KEY.equals(cookie.getName()))
                .findFirst()
                .orElse(null);
    }

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
