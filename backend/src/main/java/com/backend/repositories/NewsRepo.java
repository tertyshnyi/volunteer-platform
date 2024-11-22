package com.backend.repositories;

import com.backend.models.entity.News;
import com.backend.models.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repository interface for accessing and managing {@link News} entities in the database.
 * Extends {@link JpaRepository} to provide CRUD operations and additional query methods.
 *
 * Provides the following methods for interacting with the database:
 * - {@link #findByTitle(String)}: Finds a {@link News} entity by its title.
 * - {@link #existsByTitle(String)}: Checks if a {@link News} entity with the given title exists in the database.
 */
public interface NewsRepo extends JpaRepository<News, UUID> {
    News findByTitle(String title);
    boolean existsByTitle(String title);
}
