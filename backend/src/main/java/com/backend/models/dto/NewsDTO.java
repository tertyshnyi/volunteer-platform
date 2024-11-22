package com.backend.models.dto;

import com.backend.models.entity.News;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) for representing news information.
 *
 * This class is used to transfer data about a news article between application layers.
 * It contains fields that reflect the main attributes of the news, such as the identifier,
 * title, content, creation date, featured image, and additional images.
 * It also includes a constructor that takes a {@link News} entity object to convert it into a DTO.
 */
@Getter
@Setter
@NoArgsConstructor
public class NewsDTO {
    private UUID id;
    private String title;
    private String content;
    private long createdAt;
    private String featuredImage;
    private List<String> additionalImages;

    public NewsDTO(News news) {
        this.id = news.getId();
        this.title = news.getTitle();
        this.content = news.getContent();
        this.createdAt = news.getCreatedAt();
        this.featuredImage = news.getFeaturedImage();
        this.additionalImages = news.getAdditionalImages();
    }
}