package com.elearning.platform.dto;

import java.util.Map;

public class ProgressReportDTO {

    private Long learnerId;
    private String learnerName;
    private int lessonsCompleted;
    private Map<String, Integer> lessonSummary;

    public ProgressReportDTO(Long learnerId, String learnerName, int lessonsCompleted, Map<String, Integer> lessonSummary) {
        this.learnerId = learnerId;
        this.learnerName = learnerName;
        this.lessonsCompleted = lessonsCompleted;
        this.lessonSummary = lessonSummary;
    }

    public Long getLearnerId() {
        return learnerId;
    }

    public String getLearnerName() {
        return learnerName;
    }

    public int getLessonsCompleted() {
        return lessonsCompleted;
    }

    public Map<String, Integer> getLessonSummary() {
        return lessonSummary;
    }
}
