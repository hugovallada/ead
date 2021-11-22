package com.ead.course.repositories;

import com.ead.course.models.ModuleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ModuleRepository extends JpaRepository<ModuleModel, UUID>, JpaSpecificationExecutor<ModuleModel> {

    @EntityGraph(attributePaths = {"course"}) // qnd fizer essa consulta, o atributo como course virá com EAGER
    ModuleModel findByTitle(String title);

    @Query(value = "select * from tb_modules where course_course_id = :courseId", nativeQuery = true)
    Page<ModuleModel> findAllModulesIntoCourse(@Param("courseId") UUID courseId, Pageable pageable);

    @Modifying
    @Query(value = "delete from tb_modules where id = :id", nativeQuery = true)
    void delete(@Param("id") UUID id);

    List<ModuleModel> findAllByCourseCourseId(UUID courseId);

    Optional<ModuleModel> findByModuleIdAndCourseCourseId(UUID moduleId, UUID courseId);
}
