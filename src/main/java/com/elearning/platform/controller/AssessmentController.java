package com.elearning.platform.controller;

import com.elearning.platform.domain.Assessment;
import com.elearning.platform.domain.Submission;
import com.elearning.platform.dto.AssessmentRequest;
import com.elearning.platform.dto.SubmissionRequest;
import com.elearning.platform.service.AssessmentService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assessments")
public class AssessmentController {

    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @GetMapping("/course/{courseId}")
    public List<Assessment> list(@PathVariable Long courseId) {
        return assessmentService.listForCourse(courseId);
    }

    @PostMapping("/course/{courseId}")
    public Assessment create(@PathVariable Long courseId,
                             @RequestHeader("X-Actor-Id") Long actorId,
                             @Valid @RequestBody AssessmentRequest request) {
        return assessmentService.create(courseId, request, actorId);
    }

    @PostMapping("/{assessmentId}/submissions")
    public Submission submit(@PathVariable Long assessmentId,
                             @RequestHeader("X-Actor-Id") Long learnerId,
                             @Valid @RequestBody SubmissionRequest request) {
        return assessmentService.record(assessmentId, request, learnerId);
    }

    @GetMapping("/learner/{learnerId}")
    public List<Submission> submissions(@PathVariable Long learnerId) {
        return assessmentService.findByLearner(learnerId);
    }
}
