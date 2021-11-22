package com.ead.authuser.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseDto {

    UUID courseId;
    String name;
    String description;
    String imageUrl;
    UUID userInstructor;
    CourseStatus courseStatus;
    CourseLevel courseLevel;
}
