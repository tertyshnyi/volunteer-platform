package com.backend.util;

import com.backend.models.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

@Component
public class JwtTokenUtil {
    public static final int JWT_EXPIRATION_IN_SEC = 2592000; // 30 days in seconds
    public static final Long JWT_EXPIRATION_IN_MS = 2592000000L; // 30 days in milliseconds
    public static final String JWT_COOKIE_KEY = "auth";

    @Value("${jwt.secret.key}")
    private String secret;

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_IN_MS))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean validateToken(String token) {
//        final String tokenEmail = getEmailFromToken(token);
//        return (tokenEmail.equals(email) && !isTokenExpired(token));
        return !isTokenExpired(token);
    }

    public UUID getIdFromToken(String token) {
        String idStr = getClaimFromToken(token, Claims::getSubject);
        return UUID.fromString(idStr);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
}
