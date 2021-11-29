package com.ead.authuser.controllers;

import com.ead.authuser.clients.CourseClient;
import com.ead.authuser.dtos.CourseDto;
import com.ead.authuser.dtos.UserCourseDto;
import com.ead.authuser.service.UserCourseService;
import com.ead.authuser.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("/users/{userId/course}")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@Log4j2
@AllArgsConstructor
public class UserCourseController {

    private final CourseClient courseClient;

    private final UserService userService;

    private final UserCourseService userCourseService;

    @GetMapping
    public ResponseEntity<Page<CourseDto>> getAllCoursesByUser(
            @PageableDefault Pageable pageable,
            @PathVariable UUID userId
    ) {

        return ResponseEntity.ok(courseClient.getAllCoursesById(userId, pageable));
    }

    @PostMapping("/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable UUID userId, @RequestBody @Valid UserCourseDto dto){
        var userOptional = userService.findOne(userId);

        if(userOptional.isEmpty()) return ResponseEntity.notFound().build();

        var user = userOptional.get();

        if(userCourseService.existsByUserAndCouseId(user, userId)) {
           return ResponseEntity.status(HttpStatus.CONFLICT).body("Already exists!");
        }

        var savedUser = userCourseService.save(user.convertToUserCourseModel(dto.getCourseId()));

        return ResponseEntity.status(201).body(savedUser);
    }


}
