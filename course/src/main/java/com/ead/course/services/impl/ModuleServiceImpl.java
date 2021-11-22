package com.ead.course.services.impl;

import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.ModuleService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;

    private final LessonRepository lessonRepository;

    @Override
    @Transactional
    public void delete(ModuleModel moduleModel) {
        var lessonsList = lessonRepository.findAllLessons(moduleModel.getModuleId());

        if (!lessonsList.isEmpty()) {
            lessonRepository.deleteAll(lessonsList);
        }

        moduleRepository.delete(moduleModel);
    }

    @Override
    public void save(ModuleModel moduleModel) {
        moduleRepository.save(moduleModel);
    }

    @Override
    public Page<ModuleModel> getAll(UUID courseId, Pageable pageable, Specification<ModuleModel> spec) {
        return moduleRepository.findAllModulesIntoCourse(courseId, pageable);
    }

    @Override
    public Optional<ModuleModel> findModuleIntoCourse(UUID courseId, UUID moduleId) {
        return moduleRepository.findByModuleIdAndCourseCourseId(moduleId, courseId);
    }

    @Override
    public Optional<ModuleModel> findById(UUID moduleId) {
        return moduleRepository.findById(moduleId);
    }

    @Override
    public Page<ModuleModel> getAll(Specification<ModuleModel> spec, Pageable pageable) {
        return moduleRepository.findAll(spec, pageable);
    }


//    @Override
//    @Transactional
//    public void delete(ModuleModel moduleModel) {
//        var lessons = moduleModel.getLessons();
//
//        lessonRepository.deleteAll(lessons);
//
//        moduleRepository.delete(moduleModel);
//    }
}
