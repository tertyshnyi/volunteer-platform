package com.backend.repositories;

import com.backend.models.entity.News;
import com.backend.models.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NewsRepo extends JpaRepository<News, UUID> {
    News findByTitle(String title);
    boolean existsByTitle(String title);
}
