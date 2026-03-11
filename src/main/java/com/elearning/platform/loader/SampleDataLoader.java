package com.elearning.platform.loader;

import com.elearning.platform.domain.Assessment;
import com.elearning.platform.domain.AssessmentType;
import com.elearning.platform.domain.CommunicationThread;
import com.elearning.platform.domain.Course;
import com.elearning.platform.domain.Lesson;
import com.elearning.platform.domain.Level;
import com.elearning.platform.domain.Material;
import com.elearning.platform.domain.MaterialType;
import com.elearning.platform.domain.Message;
import com.elearning.platform.domain.ProgressRecord;
import com.elearning.platform.domain.Submission;
import com.elearning.platform.domain.UserAccount;
import com.elearning.platform.domain.UserRole;
import com.elearning.platform.repository.AssessmentRepository;
import com.elearning.platform.repository.CommunicationThreadRepository;
import com.elearning.platform.repository.CourseRepository;
import com.elearning.platform.repository.MessageRepository;
import com.elearning.platform.repository.MaterialRepository;
import com.elearning.platform.repository.ProgressRecordRepository;
import com.elearning.platform.repository.SubmissionRepository;
import com.elearning.platform.repository.UserAccountRepository;
import java.time.LocalDate;
import java.util.Arrays;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SampleDataLoader implements ApplicationRunner {

    private final CourseRepository courseRepository;
    private final UserAccountRepository userAccountRepository;
    private final MaterialRepository materialRepository;
    private final AssessmentRepository assessmentRepository;
    private final SubmissionRepository submissionRepository;
    private final ProgressRecordRepository progressRecordRepository;
    private final CommunicationThreadRepository threadRepository;
    private final MessageRepository messageRepository;
    private final PasswordEncoder passwordEncoder;

    public SampleDataLoader(CourseRepository courseRepository,
                            UserAccountRepository userAccountRepository,
                            MaterialRepository materialRepository,
                            AssessmentRepository assessmentRepository,
                            SubmissionRepository submissionRepository,
                            ProgressRecordRepository progressRecordRepository,
                            CommunicationThreadRepository threadRepository,
                            MessageRepository messageRepository,
                            PasswordEncoder passwordEncoder) {
        this.courseRepository = courseRepository;
        this.userAccountRepository = userAccountRepository;
        this.materialRepository = materialRepository;
        this.assessmentRepository = assessmentRepository;
        this.submissionRepository = submissionRepository;
        this.progressRecordRepository = progressRecordRepository;
        this.threadRepository = threadRepository;
        this.messageRepository = messageRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (courseRepository.count() > 0) {
            return;
        }

        Course agile = new Course("Product Thinking for Learners", "A practical guide to building learner-centric products.", Level.INTERMEDIATE, 18);
        agile.setPublished(true);
        Lesson research = new Lesson("Research Routines", "How to keep qualitative discovery continuous.", "https://example.com/research", 1, 25);
        Lesson experiments = new Lesson("Designing Learning Experiments", "Low-cost prototypes to validate habits.", "https://example.com/experiments", 2, 30);
        agile.addLesson(research);
        agile.addLesson(experiments);

        Course security = new Course("Secure Coding Foundations", "Core security controls that every developer should apply.", Level.BEGINNER, 12);
        security.setPublished(true);
        security.addLesson(new Lesson("Threat Modeling", "Frame risks before touching code.", "https://example.com/threats", 1, 20));
        security.addLesson(new Lesson("Secrets Management", "Use vaults and short-lived credentials.", "https://example.com/secrets", 2, 15));

        Course dataOps = new Course("DataOps Workflow Essentials", "Pipeline hygiene, observability, and resilience for analysts.", Level.INTERMEDIATE, 22);
        dataOps.setPublished(false);
        dataOps.addLesson(new Lesson("Idempotent Pipelines", "Avoid cascading failures with guardrails.", "https://example.com/idempotent", 1, 30));
        dataOps.addLesson(new Lesson("Monitoring Data Quality", "Signals you can surface cheaply.", "https://example.com/monitoring", 2, 18));

        courseRepository.saveAll(Arrays.asList(agile, security, dataOps));

        UserAccount admin = new UserAccount("System Admin", "admin@example.com", passwordEncoder.encode("password"), UserRole.ADMIN);
        UserAccount instructor = new UserAccount("Course Architect", "teacher@example.com", passwordEncoder.encode("password"), UserRole.INSTRUCTOR);
        UserAccount learner = new UserAccount("Student Ada", "student@example.com", passwordEncoder.encode("password"), UserRole.STUDENT);
        userAccountRepository.saveAll(Arrays.asList(admin, instructor, learner));

        Material material = new Material("Welcome Video", "Orientation video for the agile track", "https://example.com/video", MaterialType.VIDEO);
        material.setCourse(agile);
        material.setUploader(instructor);
        materialRepository.save(material);

        Assessment assessment = new Assessment("Sprint Quiz", "Quick check on sprint planning theory", LocalDate.now().plusWeeks(1), AssessmentType.QUIZ, 20);
        assessment.setCourse(agile);
        assessmentRepository.save(assessment);

        Submission submission = new Submission(assessment, learner, 17, "Solid reasoning", "COMPLETED");
        submissionRepository.save(submission);

        ProgressRecord record = new ProgressRecord(learner, research, 35, 85);
        progressRecordRepository.save(record);

        CommunicationThread thread = new CommunicationThread("General Questions", learner);
        threadRepository.save(thread);
        Message first = new Message("Excited to dig into the materials!", learner);
        first.setThread(thread);
        messageRepository.save(first);
        Message reply = new Message("Welcome! Drop any questions here.", instructor);
        reply.setThread(thread);
        messageRepository.save(reply);
    }
}
