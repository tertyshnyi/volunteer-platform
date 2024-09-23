package com.backend.models.dto;

import com.backend.models.entity.News;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

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