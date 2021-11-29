package com.ead.course.controllers;

import com.ead.course.clients.AuthUserClient;
import com.ead.course.dtos.SubscriptionDto;
import com.ead.course.dtos.UserDto;
import com.ead.course.dtos.UserStatus;
import com.ead.course.services.CourseService;
import com.ead.course.services.CourseUserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import javax.validation.Valid;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/courses/{courseId}/users")
@AllArgsConstructor
public class CourseUserController {

    private final AuthUserClient authUserClient;

    private final CourseService courseService;

    private final CourseUserService courseUserService;

    @GetMapping
    public ResponseEntity<Page<UserDto>> getAllUsersByCourse(@PageableDefault Pageable pageable, @PathVariable UUID courseId) {
        return ResponseEntity.ok(authUserClient.getAllUsersByCourse(pageable, courseId));
    }

    @PostMapping("/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable UUID courseId,
                                                               @RequestBody @Valid SubscriptionDto subscriptionDto) {
        var course = courseService.findById(courseId);

        if (course.isEmpty()) {
            return ResponseEntity.status(404).body("Course not found!");
        }

        if (courseUserService.existsByCourseAndUserId(course.get(), subscriptionDto.getUserId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Already reported!");
        }

        ResponseEntity<UserDto> responseUser;

        try {
            responseUser = authUserClient.getOneUserById(subscriptionDto.getUserId());
            if(responseUser.getBody().getUserStatus().equals(UserStatus.BLOCKED)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User is blocked!");
            }
        } catch (HttpStatusCodeException exception) {
            if(exception.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
            }
        } catch (NullPointerException ex) {
            return ResponseEntity.status(500).build();
        }

        var courseUserModel = courseUserService.saveAndSendSubscriptionUserInCourse(course.get().convertToCourseUserModel(subscriptionDto.getUserId()));

        return ResponseEntity.status(201).body(courseUserModel);
    }


}
