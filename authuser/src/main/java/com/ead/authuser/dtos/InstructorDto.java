package com.ead.authuser.dtos;

import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value
public class InstructorDto {

    @NotNull
    UUID userId;
}
