package com.ead.course.repositories;

import com.ead.course.models.LessonModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<LessonModel, UUID>, JpaSpecificationExecutor<LessonModel> {

    @Query(value = "Select * from tb_lessons where module_module_id = :moduleId", nativeQuery = true)
    List<LessonModel> findAllLessons(UUID moduleId);

    Page<LessonModel> findAllByModuleModuleId(UUID moduleId, Pageable pageable);

    Optional<LessonModel> findByLessonIdAndModuleModuleId(UUID lessonId, UUID moduleId);
}
