package com.ead.authuser.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value
public class InstructorDto {

    @NotNull
    UUID userId;

    @JsonCreator
    public InstructorDto(@JsonProperty("userId") UUID userId) {
        this.userId = userId;
    }
}
