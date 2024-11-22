package com.backend.repositories;

import com.backend.models.entity.OrganizationChain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repository interface for accessing and managing {@link OrganizationChain} entities in the database.
 * Extends {@link JpaRepository} to provide CRUD operations for {@link OrganizationChain} entities.
 *
 * This interface leverages Spring Data JPA to handle basic database operations such as saving,
 * updating, deleting, and retrieving {@link OrganizationChain} entities, based on their UUID identifier.
 */
public interface OrganizationChainRepo extends JpaRepository<OrganizationChain, UUID> {
}

