package com.elearning.platform.controller;

import com.elearning.platform.domain.Enrollment;
import com.elearning.platform.dto.EnrollmentRequest;
import com.elearning.platform.service.EnrollmentService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public Enrollment enroll(@Valid @RequestBody EnrollmentRequest request) {
        return enrollmentService.enroll(request);
    }

    @GetMapping("/learner/{learnerId}")
    public List<Enrollment> findByLearner(@PathVariable Long learnerId) {
        return enrollmentService.findByLearner(learnerId);
    }
}
