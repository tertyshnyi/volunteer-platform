package com.backend.repositories;

import com.backend.models.entity.OrganizationChain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrganizationChainRepo extends JpaRepository<OrganizationChain, UUID> {
}

