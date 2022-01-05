package com.ead.course.controllers;

import com.ead.course.dtos.ModuleDto;
import com.ead.course.models.ModuleModel;
import com.ead.course.services.CourseService;
import com.ead.course.services.ModuleService;
import com.ead.course.specifications.SpecificationTemplate;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/courses/{courseId}/modules")
@CrossOrigin(value = "*", maxAge = 3600)
@AllArgsConstructor
public class ModuleController {

    private final ModuleService moduleService;
    private final CourseService courseService;

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @PostMapping
    public ResponseEntity<Object> saveModule(
            @PathVariable("courseId") UUID courseId,
            @RequestBody @Valid ModuleDto moduleDto
    ) {
        var course = courseService.findById(courseId);
        if(course.isPresent()) {
            var moduleModel = new ModuleModel();
            BeanUtils.copyProperties(moduleDto, moduleModel);
            moduleModel.setCourse(course.get());

            moduleService.save(moduleModel);

            return ResponseEntity.status(HttpStatus.CREATED).body(moduleModel);
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping
    public ResponseEntity<Page<ModuleModel>> getAllModules(
            SpecificationTemplate.ModuleSpec spec,
            @PathVariable UUID courseId,
            @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(
                moduleService.getAll(SpecificationTemplate.moduleCourseId(courseId).and(spec), pageable)
        );
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @DeleteMapping("/{moduleId}")
    public ResponseEntity<Object> deleteModule(
            @PathVariable("courseId") UUID courseId,
            @PathVariable("moduleId") UUID moduleId
    ){
        var module = moduleService.findModuleIntoCourse(courseId, moduleId);

        if (module.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        moduleService.delete(module.get());

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("/{moduleId}")
    public ResponseEntity<Object> getOne(
            @PathVariable("courseId") UUID courseId,
            @PathVariable("moduleId") UUID moduleId
    ){
        var module = moduleService.findModuleIntoCourse(courseId, moduleId);
        if (module.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(module.get());
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @PutMapping("/{moduleId}")
    public ResponseEntity<Object> updateOne(
            @PathVariable("courseId") UUID courseId,
            @PathVariable("moduleId") UUID moduleId,
            @RequestBody @Valid ModuleDto moduleDto
    ) {
        var module = moduleService.findModuleIntoCourse(courseId, moduleId);

        if (module.isEmpty()) return ResponseEntity.notFound().build();

        var moduleModel = module.get();

        BeanUtils.copyProperties(moduleDto, moduleModel);

        moduleService.save(moduleModel);
        return ResponseEntity.accepted().body(moduleModel);
    }
}
