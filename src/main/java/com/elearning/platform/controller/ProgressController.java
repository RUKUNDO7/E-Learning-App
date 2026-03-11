package com.elearning.platform.controller;

import com.elearning.platform.domain.ProgressRecord;
import com.elearning.platform.dto.ProgressReportDTO;
import com.elearning.platform.dto.ProgressRequest;
import com.elearning.platform.service.ProgressService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @PostMapping("/lessons/{lessonId}/complete")
    public ProgressRecord track(@PathVariable Long lessonId,
                                @RequestHeader("X-Actor-Id") Long learnerId,
                                @Valid @RequestBody ProgressRequest request) {
        return progressService.record(lessonId, learnerId, request);
    }

    @GetMapping("/learner/{learnerId}")
    public ProgressReportDTO report(@PathVariable Long learnerId) {
        return progressService.reportByLearner(learnerId);
    }
}
