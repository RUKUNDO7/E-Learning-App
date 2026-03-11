package com.elearning.platform.service;

import com.elearning.platform.domain.Course;
import com.elearning.platform.domain.Material;
import com.elearning.platform.domain.MaterialType;
import com.elearning.platform.domain.UserAccount;
import com.elearning.platform.domain.UserRole;
import com.elearning.platform.dto.MaterialRequest;
import com.elearning.platform.repository.CourseRepository;
import com.elearning.platform.repository.MaterialRepository;
import com.elearning.platform.repository.UserAccountRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;
    private final CourseRepository courseRepository;
    private final UserService userService;

    public MaterialService(MaterialRepository materialRepository, CourseRepository courseRepository, UserService userService) {
        this.materialRepository = materialRepository;
        this.courseRepository = courseRepository;
        this.userService = userService;
    }

    public List<Material> getCourseMaterials(Long courseId) {
        return materialRepository.findByCourseId(courseId);
    }

    public Material addMaterial(Long courseId, MaterialRequest request, Long uploaderId) {
        userService.assertRole(uploaderId, UserRole.INSTRUCTOR, UserRole.ADMIN);
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("Course not found"));
        Material material = new Material(request.getTitle(), request.getDescription(), request.getResourceUrl(), request.getType());
        material.setUploader(userService.findOne(uploaderId));
        material.setCourse(course);
        return materialRepository.save(material);
    }

    public List<MaterialType> listTypes() {
        return List.of(MaterialType.values());
    }
}
