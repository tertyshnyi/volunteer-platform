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

    /**
     * Generates a JWT for the given user.
     *
     * @param user the user for whom the JWT is being generated.
     * @return the generated JWT as a string.
     */
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_IN_MS))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * Validates the provided JWT token.
     *
     * @param token the JWT token to validate.
     * @return true if the token is valid and not expired; false otherwise.
     */
    public boolean validateToken(String token) {
//        final String tokenEmail = getEmailFromToken(token);
//        return (tokenEmail.equals(email) && !isTokenExpired(token));
        return !isTokenExpired(token);
    }

    /**
     * Extracts the user ID from the given JWT token.
     *
     * @param token the JWT token to extract the user ID from.
     * @return the user ID as a UUID.
     */
    public UUID getIdFromToken(String token) {
        String idStr = getClaimFromToken(token, Claims::getSubject);
        return UUID.fromString(idStr);
    }

    /**
     * Extracts the expiration date from the given JWT token.
     *
     * @param token the JWT token to extract the expiration date from.
     * @return the expiration date.
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Extracts a specific claim from the JWT token.
     *
     * @param token the JWT token.
     * @param claimsResolver a function to extract the specific claim (e.g., subject, expiration).
     * @param <T> the type of the claim.
     * @return the extracted claim.
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Parses the JWT token and returns all claims.
     *
     * @param token the JWT token.
     * @return the claims from the token.
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * Checks whether the JWT token has expired.
     *
     * @param token the JWT token.
     * @return true if the token is expired; false otherwise.
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
}
