package com.backend.services;

import com.backend.models.entity.User;
import com.backend.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@EnableWebSecurity
@RequiredArgsConstructor
@Service
public class CustomUserDetailsSvc implements UserDetailsService {
    private final UserRepo userRepo;

    public User loadUserById(UUID id) throws UsernameNotFoundException {
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepo.findByEmail(email);
    }
}
