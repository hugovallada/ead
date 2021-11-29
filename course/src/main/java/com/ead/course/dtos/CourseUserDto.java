package com.ead.course.dtos;

import lombok.Value;

import java.util.UUID;

@Value
public class CourseUserDto {
    UUID courseId;
    UUID userId;
}
