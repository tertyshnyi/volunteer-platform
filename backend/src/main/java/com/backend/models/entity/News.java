package com.backend.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

/**
 * Entity representing a news article.
 *
 * This class maps to the "news" table in the database and contains details about a news article,
 * including its title, content, creation timestamp, featured image, and additional images.
 * The class is designed to be used in persistence operations (CRUD) and represents the structure
 * of a news article as stored in the database.
 */
@Entity
@Table(name = "news")
@Getter
@Setter
@NoArgsConstructor
public class News {

    @Id
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid4")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private long createdAt;

    // Main photo news
    @Column(nullable = false)
    private String featuredImage;

    // All photos in one news
    @ElementCollection
    @CollectionTable(name = "news_images", joinColumns = @JoinColumn(name = "news_id"))
    @Column(name = "image_url")
    private List<String> additionalImages;
}