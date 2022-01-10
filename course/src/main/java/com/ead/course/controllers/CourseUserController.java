package com.ead.course.controllers;

import com.ead.course.dtos.SubscriptionDto;
import com.ead.course.enums.UserStatus;
import com.ead.course.services.CourseService;
import com.ead.course.services.UserService;
import com.ead.course.specifications.SpecificationTemplate;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/courses/{courseId}/users")
@AllArgsConstructor
public class CourseUserController {

    private final CourseService courseService;

    private final UserService userService;

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @GetMapping
    public ResponseEntity<Object> getAllUsersByCourse(SpecificationTemplate.UserSpec spec,
                                                      @PageableDefault Pageable pageable,
                                                      @PathVariable UUID courseId) {
        var courseOptional = courseService.findById(courseId);

        if (courseOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userService.findAll(SpecificationTemplate.userCourseId(courseId).and(spec), pageable));
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @PostMapping("/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable UUID courseId,
                                                               @RequestBody @Valid SubscriptionDto subscriptionDto) {
        var course = courseService.findById(courseId);

        if (course.isEmpty()) {
            return ResponseEntity.status(404).body("Course not found!");
        }

        if (courseService.existsByCourseAndUser(courseId, subscriptionDto.getUserId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: subscription already exists");
        }

        var userModelOptional = userService.findById(subscriptionDto.getUserId());

        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        if (userModelOptional.get().getUserStatus().equals(UserStatus.BLOCKED.toString())) {
            return ResponseEntity.status(422).body("User us blocked");
        }

        courseService.saveSubscriptionUserInCourseAndSendNotification(course.get(), userModelOptional.get());

        return ResponseEntity.status(201).body("Subscription created.");
    }
}
