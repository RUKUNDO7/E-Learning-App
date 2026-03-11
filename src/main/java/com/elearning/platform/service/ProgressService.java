package com.elearning.platform.service;

import com.elearning.platform.domain.Lesson;
import com.elearning.platform.domain.ProgressRecord;
import com.elearning.platform.domain.UserAccount;
import com.elearning.platform.domain.UserRole;
import com.elearning.platform.dto.ProgressReportDTO;
import com.elearning.platform.dto.ProgressRequest;
import com.elearning.platform.repository.LessonRepository;
import com.elearning.platform.repository.ProgressRecordRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProgressService {

    private final ProgressRecordRepository progressRecordRepository;
    private final LessonRepository lessonRepository;
    private final UserService userService;

    public ProgressService(ProgressRecordRepository progressRecordRepository, LessonRepository lessonRepository, UserService userService) {
        this.progressRecordRepository = progressRecordRepository;
        this.lessonRepository = lessonRepository;
        this.userService = userService;
    }

    @Transactional
    public ProgressRecord record(Long lessonId, Long learnerId, ProgressRequest request) {
        userService.assertRole(learnerId, UserRole.STUDENT);
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new IllegalArgumentException("Lesson not found"));
        UserAccount learner = userService.findOne(learnerId);
        ProgressRecord record = new ProgressRecord(learner, lesson, request.getTimeSpentMinutes(), request.getQuizScore());
        return progressRecordRepository.save(record);
    }

    public ProgressReportDTO reportByLearner(Long learnerId) {
        List<ProgressRecord> records = progressRecordRepository.findByLearnerId(learnerId);
        UserAccount learner = userService.findOne(learnerId);
        Map<String, Integer> summary = records.stream()
                .collect(Collectors.toMap(r -> r.getLesson().getTitle(), ProgressRecord::getQuizScore, Integer::max));
        return new ProgressReportDTO(learner.getId(), learner.getName(), records.size(), summary);
    }
}
