package com.backend.config;

import com.backend.filters.JwtAuthenticationFilter;
import com.backend.services.CustomUserDetailsSvc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for setting up Spring Security in the application.
 *
 * This class configures the security settings for the application, including authentication, authorization,
 * and session management. It sets up a custom `UserDetailsService`, JWT-based authentication using a custom
 * filter (`JwtAuthenticationFilter`), and defines the security rules for various endpoints.
 */
@Configuration
public class SecurityConfiguration {

    private final CustomUserDetailsSvc customUserDetailsSvc;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Constructs a new instance of `SecurityConfiguration` with the provided services.
     *
     * @param customUserDetailsSvc the custom service for loading user details for authentication.
     * @param jwtAuthenticationFilter the JWT filter for handling authentication based on JWT tokens.
     */
    public SecurityConfiguration(CustomUserDetailsSvc customUserDetailsSvc, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.customUserDetailsSvc = customUserDetailsSvc;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Configures the security filter chain for the application.
     *
     * This method sets up various aspects of security:
     * - Disables CSRF protection, as it is not required for stateless REST APIs.
     * - Configures authorization rules for different HTTP requests, ensuring that JWT-based authentication is
     *   applied to all endpoints except those specified in the `JwtAuthenticationFilter.EXCLUDED_PATHS`.
     * - Sets the `JwtAuthenticationEntryPoint` to handle unauthorized access attempts.
     * - Configures the `UserDetailsService` to use the custom service (`customUserDetailsSvc`).
     * - Sets the session management policy to stateless, meaning no HTTP session will be created or used.
     * - Adds the `JwtAuthenticationFilter` before the default `UsernamePasswordAuthenticationFilter` to process JWTs
     *   during authentication.
     *
     * @param http the `HttpSecurity` object used to configure security settings.
     * @return the configured `SecurityFilterChain` object.
     * @throws Exception if an error occurs while configuring security.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(JwtAuthenticationFilter.EXCLUDED_PATHS.toArray(new String[0])).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint())) // Handle unauthorized requests
                .userDetailsService(customUserDetailsSvc)
                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT filter before the username/password filter
        ;

        return http.build();
    }

    /**
     * Creates the `AuthenticationManager` bean used for authentication processing.
     *
     * This bean is necessary for Spring Security to perform authentication using the custom `UserDetailsService`.
     *
     * @param authenticationConfiguration the authentication configuration used to retrieve the `AuthenticationManager`.
     * @return the configured `AuthenticationManager` bean.
     * @throws Exception if an error occurs while configuring the `AuthenticationManager`.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
