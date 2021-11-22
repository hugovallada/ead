package com.ead.course.services;

import com.ead.course.models.ModuleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface ModuleService {

    void delete(ModuleModel moduleModel);

    void save(ModuleModel moduleModel);

    Page<ModuleModel> getAll(UUID courseId, Pageable pageable, Specification<ModuleModel> spec);

    Optional<ModuleModel> findModuleIntoCourse(UUID courseId, UUID moduleId);

    Optional<ModuleModel> findById(UUID moduleId);

    Page<ModuleModel> getAll(Specification<ModuleModel> spec, Pageable pageable);
}
