package com.elearning.platform.dto;

import com.elearning.platform.domain.MaterialType;

public class MaterialDTO {

    private Long id;
    private String title;
    private String description;
    private String resourceUrl;
    private MaterialType type;

    public MaterialDTO(Long id, String title, String description, String resourceUrl, MaterialType type) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.resourceUrl = resourceUrl;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public MaterialType getType() {
        return type;
    }
}
