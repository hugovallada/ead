package com.ead.authuser.dtos;

import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value
public class UserCourseDto {
    @NotNull
    UUID userId;
    @NotNull
    UUID courseId;
}
