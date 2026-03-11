package com.elearning.platform.controller;

import com.elearning.platform.domain.Lesson;
import com.elearning.platform.domain.Level;
import com.elearning.platform.dto.CourseDetailDTO;
import com.elearning.platform.dto.CourseSummaryDTO;
import com.elearning.platform.dto.LessonDTO;
import com.elearning.platform.dto.LessonRequest;
import com.elearning.platform.service.CourseService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<CourseSummaryDTO> listCourses(@RequestParam(required = false) Level level,
                                               @RequestParam(defaultValue = "false") boolean includeDrafts) {
        return courseService.listCourses(level, includeDrafts);
    }

    @GetMapping("/{id}")
    public CourseDetailDTO getCourse(@PathVariable Long id) {
        return courseService.getCourseDetail(id);
    }

    @PostMapping("/{id}/lessons")
    @ResponseStatus(HttpStatus.CREATED)
    public LessonDTO addLesson(@PathVariable Long id, @Valid @RequestBody LessonRequest request) {
        Lesson lesson = new Lesson(request.getTitle(), request.getSummary(), request.getContentUrl(), request.getSequenceNumber(), request.getDurationMinutes());
        Lesson saved = courseService.addLesson(id, lesson);
        return new LessonDTO(saved.getId(), saved.getTitle(), saved.getSummary(), saved.getContentUrl(), saved.getSequenceNumber(), saved.getDurationMinutes());
    }
}
