package com.elearning.platform.dto;

public class LessonDTO {

    private Long id;
    private String title;
    private String summary;
    private String contentUrl;
    private Integer sequenceNumber;
    private Integer durationMinutes;

    public LessonDTO(Long id, String title, String summary, String contentUrl, Integer sequenceNumber, Integer durationMinutes) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.contentUrl = contentUrl;
        this.sequenceNumber = sequenceNumber;
        this.durationMinutes = durationMinutes;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }
}
