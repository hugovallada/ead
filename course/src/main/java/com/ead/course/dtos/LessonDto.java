package com.ead.course.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonDto {

    @NotBlank
    String title;

    String description;

    @NotBlank
    String videoUrl;
}
