package com.elearning.platform.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EnrollmentRequest {

    @NotBlank
    private String learnerName;

    @NotBlank
    @Email
    private String learnerEmail;

    @NotNull
    private Long courseId;

    public String getLearnerName() {
        return learnerName;
    }

    public void setLearnerName(String learnerName) {
        this.learnerName = learnerName;
    }

    public String getLearnerEmail() {
        return learnerEmail;
    }

    public void setLearnerEmail(String learnerEmail) {
        this.learnerEmail = learnerEmail;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
