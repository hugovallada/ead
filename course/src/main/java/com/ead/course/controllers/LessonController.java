package com.ead.course.controllers;

import com.ead.course.dtos.LessonDto;
import com.ead.course.models.LessonModel;
import com.ead.course.services.LessonService;
import com.ead.course.services.ModuleService;
import com.ead.course.specifications.SpecificationTemplate;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/modules/{moduleId}/lessons")
@CrossOrigin(value = "*", maxAge = 3600)
@AllArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    private final ModuleService moduleService;

    @PostMapping
    public ResponseEntity<Object> createLesson(
            @PathVariable UUID moduleId,
            @RequestBody @Valid LessonDto lessonDto
    ) {
        var module = moduleService.findById(moduleId);

        if (module.isPresent()) {
            var lesson = new LessonModel();
            BeanUtils.copyProperties(lessonDto, lesson);
            lesson.setModule(module.get());

            lessonService.save(lesson);
            return ResponseEntity.status(HttpStatus.CREATED).body(lesson);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<Page<LessonModel>> getAll(
            SpecificationTemplate.LessonSpec spec,
            @PathVariable UUID moduleId,
            @PageableDefault Pageable pageable
    ) {
        return ResponseEntity.ok(lessonService.findAll(
                SpecificationTemplate.lessonModuleId(moduleId).and(spec), pageable
        ));
    }

    @DeleteMapping("/{lessonId}")
    public ResponseEntity<Object> deleteOne(
            @PathVariable UUID moduleId,
            @PathVariable UUID lessonId
    ) {
        var lesson = lessonService.findLessonIntoModel(lessonId, moduleId);

        if (lesson.isPresent()) {
            lessonService.delete(lesson.get());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{lessonId}")
    public ResponseEntity<Object> updateLesson(
            @PathVariable UUID moduleId,
            @PathVariable UUID lessonId,
            @RequestBody @Valid LessonDto lessonDto
    ) {
        var lesson = lessonService.findLessonIntoModel(lessonId, moduleId);

        if (lesson.isPresent()) {
            var lessonModel = lesson.get();

            BeanUtils.copyProperties(lessonDto, lessonModel);

            lessonService.save(lessonModel);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(lesson);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{lessonId}")
    public ResponseEntity<Object> getLesson(
            @PathVariable UUID moduleId,
            @PathVariable UUID lessonId
    ) {
        var lesson = lessonService.findLessonIntoModel(lessonId, moduleId);

        if (lesson.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(lesson.get());
    }


}
