package com.backend.filters;

import com.backend.models.rest.response.RestResponseBody;
import com.backend.services.CustomUserDetailsSvc;
import com.backend.util.BearerHeaderExtractor;
import com.backend.util.CookieExtractor;
import com.backend.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final List<String> EXCLUDED_PATHS = List.of("/api/v1/public/**", "/favicon.ico");
    private static final String FAILED_MSG = "The authorization token has failed validation.";
    private static final String MISSING_MSG = "The authorization token is missing. Provide Bearer or cookie 'auth'.";
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private final JwtTokenUtil jwtTokenUtil;

    private final CustomUserDetailsSvc customUserDetailsSvc;

    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil, CustomUserDetailsSvc customUserDetailsSvc) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.customUserDetailsSvc = customUserDetailsSvc;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        // Check if the request URI matches any of the excluded paths
        if (EXCLUDED_PATHS.stream().anyMatch(path -> antPathMatcher.match(path, requestURI))) {
            chain.doFilter(request, response); // Skip JWT validation for this request
            return;
        }

        String jwtToken;
        UUID userId;

        // Check Authorization header first
        String header = request.getHeader("Authorization");
        if (header != null) {
            jwtToken = BearerHeaderExtractor.extract(header);
        } else {
            // Check the auth cookie
            Cookie[] cookies = request.getCookies();
            jwtToken = CookieExtractor.extractValue(cookies, JwtTokenUtil.JWT_COOKIE_KEY);
        }

        // Validate the JWT token and authenticate the user
        if (jwtToken != null) {
            try {
                userId = jwtTokenUtil.getIdFromToken(jwtToken);
            } catch (Exception e) {
                sendErrorResponse(response, FAILED_MSG);
                return;
            }
        } else {
            sendErrorResponse(response, MISSING_MSG);
            return;
        }

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.customUserDetailsSvc.loadUserById(userId);
            if (userDetails == null) {
                sendErrorResponse(response, FAILED_MSG);
                return;
            }

            if (jwtTokenUtil.validateToken(jwtToken)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                sendErrorResponse(response, FAILED_MSG);
                return;
            }
        } else {
            sendErrorResponse(response, FAILED_MSG);
            return;
        }

        chain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        RestResponseBody<?> restResponseBody = new RestResponseBody<>(false, message, null);
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = mapper.writeValueAsString(restResponseBody);
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestURI = request.getRequestURI();
        return EXCLUDED_PATHS.stream().anyMatch(path -> antPathMatcher.match(path, requestURI));
    }
}
