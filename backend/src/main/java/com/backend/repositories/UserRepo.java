package com.backend.repositories;

import com.backend.models.entity.User;
import com.backend.models.entity.UserConfidential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

/**
 * Repository interface for accessing and managing {@link User} and {@link UserConfidential} entities in the database.
 * Extends {@link JpaRepository} to provide CRUD operations for {@link User} entities and custom queries for
 * retrieving {@link UserConfidential} entities.
 *
 * This interface allows operations such as saving, updating, deleting, and retrieving {@link User} entities
 * based on their UUID identifier. It also provides custom methods to find users by name, surname, email,
 * and check if a user exists by email. Additionally, it includes queries for retrieving {@link UserConfidential}
 * information by user ID or email.
 */
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
