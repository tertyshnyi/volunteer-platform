package com.backend.util;

import org.springframework.stereotype.Component;

@Component
public class BearerHeaderExtractor {
    public static String extract(String header) {
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
