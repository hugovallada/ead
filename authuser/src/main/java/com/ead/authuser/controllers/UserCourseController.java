package com.ead.authuser.controllers;

import com.ead.authuser.clients.CourseClient;
import com.ead.authuser.dtos.CourseDto;
import com.ead.authuser.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/users/{userId/course}")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@Log4j2
@AllArgsConstructor
public class UserCourseController {

    private final CourseClient courseClient;

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<CourseDto>> getAllCoursesByUser(
            @PageableDefault Pageable pageable,
            @PathVariable UUID userId
    ) {
        var userModelOptional = userService.findOne(userId);
        if (userModelOptional.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(courseClient.getAllCoursesById(userId, pageable));
    }

}
