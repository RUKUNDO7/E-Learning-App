package com.elearning.platform.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "learner_id", nullable = false)
    private UserAccount learner;

    private LocalDateTime enrolledAt = LocalDateTime.now();

    private Integer progressPercent = 0;

    private String status = "ACTIVE";

    public Enrollment() {
    }

    public Enrollment(Course course, UserAccount learner) {
        this.course = course;
        this.learner = learner;
    }

    public Long getId() {
        return id;
    }

    public Course getCourse() {
        return course;
    }

    public UserAccount getLearner() {
        return learner;
    }

    public LocalDateTime getEnrolledAt() {
        return enrolledAt;
    }

    public Integer getProgressPercent() {
        return progressPercent;
    }

    public void setProgressPercent(Integer progressPercent) {
        this.progressPercent = progressPercent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
