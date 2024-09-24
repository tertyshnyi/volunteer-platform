package com.backend.repositories;

import com.backend.models.entity.User;
import com.backend.models.entity.UserConfidential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {

    User findByName(String name);

    User findBySurname(String surname);

    User findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT r FROM UserConfidential r WHERE r.id = :id")
    UserConfidential getUserConfidentialById(@Param("id") UUID id);

    @Query("SELECT r FROM UserConfidential r WHERE r.email = :email")
    UserConfidential getUserConfidentialByEmail(@Param("email") String email);

}
