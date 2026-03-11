package com.elearning.platform.repository;

import com.elearning.platform.domain.Course;
import com.elearning.platform.domain.Level;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByLevel(Level level);
    List<Course> findByPublishedTrue();
}
