package com.backend.services;

import com.backend.models.entity.User;
import com.backend.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Custom service for loading {@link User} details used by Spring Security for authentication and authorization.
 * Implements the {@link UserDetailsService} interface to provide methods for retrieving user details by
 * email (used for login) or user ID (for internal purposes).
 *
 * The service interacts with the {@link UserRepo} repository to fetch user information from the database.
 * It also provides an implementation of {@link UserDetailsService#loadUserByUsername(String)},
 * which is required by Spring Security to authenticate users based on their email.
 */
@EnableWebSecurity
@RequiredArgsConstructor
@Service
public class CustomUserDetailsSvc implements UserDetailsService {
    private final UserRepo userRepo;

    /**
     * Load a {@link User} by their UUID.
     *
     * This method is used when the user's ID (UUID) is known, for example, when retrieving user information
     * in other service layers or processes.
     *
     * @param id the UUID of the user.
     * @return the {@link User} with the specified ID, or {@code null} if no such user is found.
     * @throws UsernameNotFoundException if the user is not found.
     */
    public User loadUserById(UUID id) throws UsernameNotFoundException {
        return userRepo.findById(id).orElse(null);
    }

    /**
     * Load a {@link User} by their email address.
     *
     * This method is used by Spring Security to authenticate users during login.
     *
     * @param email the email address of the user.
     * @return the {@link User} with the specified email.
     * @throws UsernameNotFoundException if no user with the given email is found.
     */
    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepo.findByEmail(email);
    }
}
