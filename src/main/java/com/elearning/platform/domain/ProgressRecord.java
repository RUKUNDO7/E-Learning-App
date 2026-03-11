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
@Table(name = "progress_records")
public class ProgressRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "learner_id")
    private UserAccount learner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    private LocalDateTime completedAt = LocalDateTime.now();

    private Integer timeSpentMinutes;

    private Integer quizScore;

    public ProgressRecord() {
    }

    public ProgressRecord(UserAccount learner, Lesson lesson, Integer timeSpentMinutes, Integer quizScore) {
        this.learner = learner;
        this.lesson = lesson;
        this.timeSpentMinutes = timeSpentMinutes;
        this.quizScore = quizScore;
    }

    public Long getId() {
        return id;
    }

    public UserAccount getLearner() {
        return learner;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public Integer getTimeSpentMinutes() {
        return timeSpentMinutes;
    }

    public void setTimeSpentMinutes(Integer timeSpentMinutes) {
        this.timeSpentMinutes = timeSpentMinutes;
    }

    public Integer getQuizScore() {
        return quizScore;
    }

    public void setQuizScore(Integer quizScore) {
        this.quizScore = quizScore;
    }
}
