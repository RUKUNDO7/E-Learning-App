package com.elearning.platform.service;

import com.elearning.platform.domain.Assessment;
import com.elearning.platform.domain.Submission;
import com.elearning.platform.domain.UserRole;
import com.elearning.platform.dto.AssessmentRequest;
import com.elearning.platform.dto.SubmissionRequest;
import com.elearning.platform.repository.AssessmentRepository;
import com.elearning.platform.repository.CourseRepository;
import com.elearning.platform.repository.SubmissionRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final SubmissionRepository submissionRepository;
    private final CourseRepository courseRepository;
    private final UserService userService;

    public AssessmentService(AssessmentRepository assessmentRepository,
                             SubmissionRepository submissionRepository,
                             CourseRepository courseRepository,
                             UserService userService) {
        this.assessmentRepository = assessmentRepository;
        this.submissionRepository = submissionRepository;
        this.courseRepository = courseRepository;
        this.userService = userService;
    }

    public List<Assessment> listForCourse(Long courseId) {
        return assessmentRepository.findByCourseId(courseId);
    }

    @Transactional
    public Assessment create(Long courseId, AssessmentRequest request, Long actorId) {
        userService.assertRole(actorId, UserRole.INSTRUCTOR, UserRole.ADMIN);
        var course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("Course not found"));
        var assessment = new Assessment(request.getTitle(), request.getDescription(), request.getDueDate(), request.getType(), request.getMaxScore());
        assessment.setCourse(course);
        return assessmentRepository.save(assessment);
    }

    @Transactional
    public Submission record(Long assessmentId, SubmissionRequest request, Long learnerId) {
        userService.assertRole(learnerId, UserRole.STUDENT);
        var assessment = assessmentRepository.findById(assessmentId).orElseThrow(() -> new IllegalArgumentException("Assessment not found"));
        var learner = userService.findOne(learnerId);
        var submission = new Submission(assessment, learner, request.getScore(), request.getFeedback(), "COMPLETED");
        return submissionRepository.save(submission);
    }

    public List<Submission> findByLearner(Long learnerId) {
        return submissionRepository.findByLearnerId(learnerId);
    }
}
