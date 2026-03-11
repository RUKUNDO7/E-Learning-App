package com.elearning.platform.repository;

import com.elearning.platform.domain.Submission;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByLearnerId(Long learnerId);
}
