package com.backend.models.rest.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
