package com.ead.course.controllers;

import com.ead.course.clients.CourseClient;
import com.ead.course.dtos.UserDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/courses/{courseId}/users")
@AllArgsConstructor
public class CourseUserController {

    private final CourseClient courseClient;

    @GetMapping
    public ResponseEntity<Page<UserDto>> getAllUsersByCourse(@PageableDefault Pageable pageable, @PathVariable UUID courseId) {
        return ResponseEntity.ok(courseClient.getAllUsersByCourse(pageable, courseId));
    }
}
