package com.backend.repositories;

import com.backend.models.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrganizationRepo extends JpaRepository<Organization, UUID> {

    Organization findByName(String name);

    boolean existsByName(String name);
}
