package com.elearning.platform.service;

import com.elearning.platform.domain.Course;
import com.elearning.platform.domain.Enrollment;
import com.elearning.platform.domain.UserAccount;
import com.elearning.platform.dto.EnrollmentRequest;
import com.elearning.platform.repository.CourseRepository;
import com.elearning.platform.repository.EnrollmentRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final UserService userService;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, CourseRepository courseRepository, UserService userService) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
        this.userService = userService;
    }

    @Transactional
    public Enrollment enroll(EnrollmentRequest request) {
        UserAccount learner = userService.createOrGetStudent(request.getLearnerName(), request.getLearnerEmail());
        Course course = courseRepository.findById(request.getCourseId()).orElseThrow(() -> new IllegalArgumentException("Course not found"));
        Enrollment enrollment = new Enrollment(course, learner);
        enrollment.setStatus("ACTIVE");
        return enrollmentRepository.save(enrollment);
    }

    @Transactional(readOnly = true)
    public List<Enrollment> findByLearner(Long learnerId) {
        return enrollmentRepository.findByLearnerId(learnerId);
    }
}
