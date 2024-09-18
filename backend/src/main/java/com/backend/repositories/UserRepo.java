package com.backend.repositories;

import com.backend.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {

    User findByName(String name);

    boolean existsByEmail(String name);
}
