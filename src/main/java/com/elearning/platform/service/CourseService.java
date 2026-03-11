package com.elearning.platform.service;

import com.elearning.platform.domain.Course;
import com.elearning.platform.domain.Lesson;
import com.elearning.platform.domain.Material;
import com.elearning.platform.domain.Level;
import com.elearning.platform.dto.CourseDetailDTO;
import com.elearning.platform.dto.CourseSummaryDTO;
import com.elearning.platform.dto.LessonDTO;
import com.elearning.platform.dto.MaterialDTO;
import com.elearning.platform.repository.CourseRepository;
import com.elearning.platform.repository.LessonRepository;
import com.elearning.platform.repository.MaterialRepository;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final MaterialRepository materialRepository;

    public CourseService(CourseRepository courseRepository, LessonRepository lessonRepository, MaterialRepository materialRepository) {
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
        this.materialRepository = materialRepository;
    }

    public List<CourseSummaryDTO> listCourses(Level filterLevel, boolean includeDrafts) {
        List<Course> raw = filterLevel == null ? includeDrafts ? courseRepository.findAll() : courseRepository.findByPublishedTrue()
                : courseRepository.findByLevel(filterLevel);
        return raw.stream()
                .sorted(Comparator.comparing(Course::getCreatedAt).reversed())
                .map(course -> new CourseSummaryDTO(course.getId(), course.getTitle(), course.getLevel(), course.getEstimatedHours(), course.isPublished()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CourseDetailDTO getCourseDetail(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("Course not found"));
        List<LessonDTO> lessons = lessonRepository.findByCourseId(courseId).stream()
                .sorted(Comparator.comparing(Lesson::getSequenceNumber))
                .map(this::toLessonDTO)
                .collect(Collectors.toList());
        List<MaterialDTO> materials = materialRepository.findByCourseId(courseId).stream()
                .map(this::toMaterialDTO)
                .collect(Collectors.toList());
        return new CourseDetailDTO(course.getId(), course.getTitle(), course.getDescription(), course.getLevel(), course.getEstimatedHours(), course.isPublished(), lessons, materials);
    }

    @Transactional
    public Lesson addLesson(Long courseId, Lesson lesson) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("Course not found"));
        lesson.setCourse(course);
        course.getLessons().add(lesson);
        courseRepository.save(course);
        return lessonRepository.save(lesson);
    }

    private LessonDTO toLessonDTO(Lesson lesson) {
        return new LessonDTO(lesson.getId(), lesson.getTitle(), lesson.getSummary(), lesson.getContentUrl(), lesson.getSequenceNumber(), lesson.getDurationMinutes());
    }

    private MaterialDTO toMaterialDTO(Material material) {
        return new MaterialDTO(material.getId(), material.getTitle(), material.getDescription(), material.getResourceUrl(), material.getType());
    }
}
