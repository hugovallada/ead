package com.ead.course.services.impl;

import com.ead.course.models.LessonModel;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.services.LessonService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;


    @Override
    public void save(LessonModel lessonModel) {
        lessonRepository.save(lessonModel);
    }

    @Override
    public Page<LessonModel> findAll(UUID moduleId, Pageable pageable) {
        return lessonRepository.findAllByModuleModuleId(moduleId, pageable);
    }

    @Override
    public Optional<LessonModel> findLessonIntoModel(UUID lessonId, UUID moduleId) {
        return lessonRepository.findByLessonIdAndModuleModuleId(lessonId, moduleId);
    }

    @Override
    public void delete(LessonModel lessonModel) {
        lessonRepository.delete(lessonModel);
    }

    @Override
    public Page<LessonModel> findAll(Specification<LessonModel> spec, Pageable pageable) {
        return lessonRepository.findAll(spec, pageable);
    }


}
