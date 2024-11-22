package com.backend.repositories;

import com.backend.models.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repository interface for accessing and managing {@link Organization} entities in the database.
 * Extends {@link JpaRepository} to provide CRUD operations for {@link Organization} entities.
 *
 * This interface allows operations such as saving, updating, deleting, and retrieving {@link Organization} entities
 * based on their UUID identifier. Additionally, it includes custom methods for retrieving organizations by their name
 * and checking if an organization exists by name.
 */
public interface OrganizationRepo extends JpaRepository<Organization, UUID> {

    Organization findByName(String name);

    boolean existsByName(String name);
}
