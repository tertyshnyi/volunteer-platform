package com.backend.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Commences an authentication scheme by sending an HTTP `401 Unauthorized` response.
     *
     * This method is invoked when an unauthenticated request is made to a secured endpoint.
     * It sends a `401 Unauthorized` error response to the client, indicating that the user is not authenticated
     * and needs to provide valid credentials (such as a valid JWT token) to access the resource.
     *
     * This is part of the Spring Security framework's authentication process, where this method is invoked
     * if the user attempts to access a resource that requires authentication but does not provide valid credentials.
     *
     * @param request the HTTP request that triggered the authentication error.
     * @param response the HTTP response that will be sent back to the client with the error.
     * @param authException the exception that was thrown due to the authentication failure.
     * @throws IOException if an input or output error occurs during handling the response.
     * @throws ServletException if the request processing encounters a general servlet-related error.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
