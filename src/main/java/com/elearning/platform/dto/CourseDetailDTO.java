package com.elearning.platform.dto;

import com.elearning.platform.domain.Level;
import java.util.List;

public class CourseDetailDTO {

    private Long id;
    private String title;
    private String description;
    private Level level;
    private Integer estimatedHours;
    private boolean published;
    private List<LessonDTO> lessons;
    private List<MaterialDTO> materials;

    public CourseDetailDTO(Long id, String title, String description, Level level, Integer estimatedHours, boolean published, List<LessonDTO> lessons, List<MaterialDTO> materials) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.level = level;
        this.estimatedHours = estimatedHours;
        this.published = published;
        this.lessons = lessons;
        this.materials = materials;
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

    public Level getLevel() {
        return level;
    }

    public Integer getEstimatedHours() {
        return estimatedHours;
    }

    public boolean isPublished() {
        return published;
    }

    public List<LessonDTO> getLessons() {
        return lessons;
    }

    public List<MaterialDTO> getMaterials() {
        return materials;
    }
}
