package com.ead.course.services;

import com.ead.course.models.LessonModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface LessonService {


    void save(LessonModel lessonModel);

    Page<LessonModel> findAll(UUID moduleId, Pageable pageable);

    Optional<LessonModel> findLessonIntoModel(UUID lessonId, UUID moduleId);

    void delete(LessonModel lessonModel);

    Page<LessonModel> findAll(Specification<LessonModel> spec, Pageable pageable);

}
