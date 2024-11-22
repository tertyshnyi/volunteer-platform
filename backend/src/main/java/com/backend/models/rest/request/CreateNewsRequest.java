package com.backend.models.rest.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * This class represents a request to create a news article. It contains information
 * required to create a news entry, including the title, content, a featured image,
 * and additional images. It is used as part of the request payload for creating news articles.
 *
 * The request includes the following fields:
 * - `title`: The title of the news article.
 * - `content`: The content or body of the news article.
 * - `featuredImage`: A string URL representing the featured image for the news article.
 * - `additionalImages`: A list of strings representing URLs of additional images related to the news article.
 *
 * It also provides a utility method `isComplete()`, which checks whether all the required fields are provided
 * in order to confirm the completeness of the request before processing.
 */
@Getter
@Setter
public class CreateNewsRequest {
    private String title;
    private String content;
    private String featuredImage;
    private List<String> additionalImages;

    public boolean isComplete(){
        return title != null && content != null && featuredImage != null && additionalImages != null;
    }
}
