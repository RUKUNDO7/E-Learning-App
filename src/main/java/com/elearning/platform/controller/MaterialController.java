package com.elearning.platform.controller;

import com.elearning.platform.domain.Material;
import com.elearning.platform.domain.MaterialType;
import com.elearning.platform.dto.MaterialRequest;
import com.elearning.platform.service.MaterialService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/materials")
public class MaterialController {

    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping("/course/{id}")
    public List<Material> getByCourse(@PathVariable Long id) {
        return materialService.getCourseMaterials(id);
    }

    @PostMapping("/course/{id}")
    public Material upload(@PathVariable Long id,
                           @RequestHeader("X-Actor-Id") Long uploaderId,
                           @Valid @RequestBody MaterialRequest request) {
        return materialService.addMaterial(id, request, uploaderId);
    }

    @GetMapping("/types")
    public List<MaterialType> listTypes() {
        return materialService.listTypes();
    }
}
