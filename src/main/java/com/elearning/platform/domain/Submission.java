package com.elearning.platform.domain;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
@Table(name = "submissions")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assessment_id")
    private Assessment assessment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "learner_id")
    private UserAccount learner;

    private LocalDateTime submittedAt = LocalDateTime.now();

    private Integer score;

    private String feedback;

    @Column(length = 100)
    private String status = "PENDING";

    public Submission() {
    }

    public Submission(Assessment assessment, UserAccount learner, Integer score, String feedback, String status) {
        this.assessment = assessment;
        this.learner = learner;
        this.score = score;
        this.feedback = feedback;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public UserAccount getLearner() {
        return learner;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
