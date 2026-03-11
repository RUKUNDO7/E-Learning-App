package com.elearning.platform.repository;

import com.elearning.platform.domain.ProgressRecord;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgressRecordRepository extends JpaRepository<ProgressRecord, Long> {
    List<ProgressRecord> findByLearnerId(Long learnerId);
}
