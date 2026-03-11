package com.elearning.platform.dto;

import com.elearning.platform.domain.Level;

public class CourseSummaryDTO {

    private Long id;
    private String title;
    private Level level;
    private Integer estimatedHours;
    private boolean published;

    public CourseSummaryDTO(Long id, String title, Level level, Integer estimatedHours, boolean published) {
        this.id = id;
        this.title = title;
        this.level = level;
        this.estimatedHours = estimatedHours;
        this.published = published;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Level getLevel() {
        return level;
    }

    public Integer getEstimatedHours() {
        return estimatedHours;
    }

    public boolean isPublished() {
        return published;
    }
}
